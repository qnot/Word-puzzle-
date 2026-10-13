package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Level
import com.example.model.SceneObject
import kotlin.math.hypot
import kotlin.math.roundToInt

@Composable
fun InteractiveSceneView(
    level: Level,
    discoveredObjectIds: Set<String>,
    activeTargetObjectId: String?,
    isHintGlowing: Boolean,
    onObjectTapped: (SceneObject) -> Unit,
    onMissedTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    BoxWithConstraints(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .border(3.dp, Color(level.accentColor).copy(alpha = 0.4f), RoundedCornerShape(24.dp))
            .shadow(6.dp, RoundedCornerShape(24.dp))
            .testTag("interactive_scene_container")
    ) {
        val widthPx = constraints.maxWidth.toFloat()
        val heightPx = constraints.maxHeight.toFloat()
        val minDim = minOf(widthPx, heightPx)

        // Backdrop Scene Canvas (Architectural, Nature & Perspective room)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(level.id, discoveredObjectIds) {
                    detectTapGestures { offset ->
                        val hitObject = level.sceneObjects.firstOrNull { obj ->
                            val objX = obj.xRatio * size.width
                            val objY = obj.yRatio * size.height
                            val radius = (obj.radiusRatio * minDim).coerceAtLeast(42f)
                            hypot(offset.x - objX, offset.y - objY) <= radius
                        }
                        if (hitObject != null) {
                            onObjectTapped(hitObject)
                        } else {
                            onMissedTap()
                        }
                    }
                }
        ) {
            val w = size.width
            val h = size.height

            // 1. Sky / Wall gradient
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(level.skyGradientStart),
                        Color(level.skyGradientEnd)
                    ),
                    startY = 0f,
                    endY = h * 0.65f
                ),
                size = androidx.compose.ui.geometry.Size(w, h * 0.65f)
            )

            // 2. Floor / Ground
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(level.floorColor),
                        Color(level.wallColor)
                    ),
                    startY = h * 0.65f,
                    endY = h
                ),
                topLeft = Offset(0f, h * 0.65f),
                size = androidx.compose.ui.geometry.Size(w, h * 0.35f)
            )

            // 3. Room Baseboard / Horizon line
            drawLine(
                color = Color(level.accentColor).copy(alpha = 0.5f),
                start = Offset(0f, h * 0.65f),
                end = Offset(w, h * 0.65f),
                strokeWidth = 4f
            )

            // 4. Subtle scenic decor lines (perspective grid on floor)
            val floorStep = w / 6f
            for (i in 0..6) {
                val startX = i * floorStep
                val endX = w * 0.5f + (startX - w * 0.5f) * 1.5f
                drawLine(
                    color = Color.White.copy(alpha = 0.15f),
                    start = Offset(startX, h * 0.65f),
                    end = Offset(endX, h),
                    strokeWidth = 2f
                )
            }

            // 5. Environmental backdrop features
            when (level.id) {
                1 -> { // Bedroom Window
                    val winX = w * 0.35f
                    val winY = h * 0.22f
                    val winW = w * 0.24f
                    val winH = h * 0.22f
                    drawRoundRect(
                        color = Color(0xFF60A5FA).copy(alpha = 0.35f),
                        topLeft = Offset(winX - winW / 2, winY - winH / 2),
                        size = androidx.compose.ui.geometry.Size(winW, winH),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
                    )
                    drawRoundRect(
                        color = Color.White.copy(alpha = 0.8f),
                        topLeft = Offset(winX - winW / 2, winY - winH / 2),
                        size = androidx.compose.ui.geometry.Size(winW, winH),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f),
                        style = Stroke(width = 4f)
                    )
                }
                4 -> { // Garden Hills
                    val hillPath = Path().apply {
                        moveTo(0f, h * 0.65f)
                        quadraticTo(w * 0.25f, h * 0.50f, w * 0.5f, h * 0.60f)
                        quadraticTo(w * 0.75f, h * 0.52f, w, h * 0.65f)
                        lineTo(w, h)
                        lineTo(0f, h)
                        close()
                    }
                    drawPath(hillPath, color = Color(0xFF10B981).copy(alpha = 0.25f))
                }
                5 -> { // Beach Waves
                    val wavePath = Path().apply {
                        moveTo(0f, h * 0.65f)
                        quadraticTo(w * 0.3f, h * 0.61f, w * 0.6f, h * 0.66f)
                        quadraticTo(w * 0.85f, h * 0.62f, w, h * 0.65f)
                        lineTo(w, h * 0.70f)
                        lineTo(0f, h * 0.70f)
                        close()
                    }
                    drawPath(wavePath, color = Color(0xFF38BDF8).copy(alpha = 0.4f))
                }
                9 -> { // Space Stars
                    val stars = listOf(
                        Offset(w * 0.15f, h * 0.15f),
                        Offset(w * 0.45f, h * 0.10f),
                        Offset(w * 0.85f, h * 0.18f),
                        Offset(w * 0.65f, h * 0.30f),
                        Offset(w * 0.10f, h * 0.45f)
                    )
                    for (s in stars) {
                        drawCircle(color = Color.White.copy(alpha = 0.8f), radius = 3.5f, center = s)
                    }
                }
            }
        }

        // Scene Objects rendered as interactive tactile nodes
        level.sceneObjects.forEach { obj ->
            val isDiscovered = discoveredObjectIds.contains(obj.id)
            val isTarget = obj.id == activeTargetObjectId
            val isGlowing = isTarget && isHintGlowing

            val xDp = (obj.xRatio * maxWidth.value).dp
            val yDp = ((obj.yRatio * maxHeight.value) + if (isTarget) floatOffset else 0f).dp

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = (obj.xRatio * widthPx - 38.dp.toPx()).roundToInt(),
                            y = ((obj.yRatio * heightPx - 38.dp.toPx()) + if (isTarget) floatOffset * 3f else 0f).roundToInt()
                        )
                    }
                    .testTag("scene_object_${obj.id}"),
                contentAlignment = Alignment.Center
            ) {
                // Hint Pulsing Ring
                if (isGlowing) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .scale(pulseScale)
                            .border(3.5.dp, Color(0xFFFBBF24), CircleShape)
                            .background(Color(0xFFFDE047).copy(alpha = 0.25f), CircleShape)
                    )
                }

                // Interactive Object Bubble
                Surface(
                    onClick = { onObjectTapped(obj) },
                    shape = RoundedCornerShape(18.dp),
                    color = if (isDiscovered) {
                        Color(0xFF10B981).copy(alpha = 0.20f)
                    } else {
                        Color(obj.primaryColor).copy(alpha = 0.95f)
                    },
                    border = androidx.compose.foundation.BorderStroke(
                        width = if (isTarget && isHintGlowing) 3.dp else 2.dp,
                        color = when {
                            isGlowing -> Color(0xFFF59E0B)
                            isDiscovered -> Color(0xFF10B981)
                            else -> Color(obj.secondaryColor)
                        }
                    ),
                    shadowElevation = if (isDiscovered) 2.dp else 6.dp,
                    modifier = Modifier.size(76.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            // Icon or letter emblem
                            Text(
                                text = when (obj.iconKey) {
                                    "bed" -> "🛏️"
                                    "light" -> "💡"
                                    "menu_book" -> "📖"
                                    "schedule" -> "⏰"
                                    "cloud" -> "☁️"
                                    "layers" -> "🧶"
                                    "window" -> "🪟"
                                    "pets" -> "🧸"
                                    "kitchen" -> "🧊"
                                    "eco" -> "🍌"
                                    "nutrition" -> "🍎"
                                    "local_cafe" -> "☕"
                                    "coffee" -> "🫖"
                                    "microwave" -> "🍳"
                                    "cake" -> "🍪"
                                    "space_dashboard" -> "📋"
                                    "backpack" -> "🎒"
                                    "public" -> "🌍"
                                    "straighten" -> "📏"
                                    "notifications" -> "🔔"
                                    "edit" -> "✏️"
                                    "flutter" -> "🦋"
                                    "park" -> "🌳"
                                    "wb_sunny" -> "🌻"
                                    "water_drop" -> "🚿"
                                    "pest_control" -> "🐞"
                                    "beach_access" -> "⛱️"
                                    "star" -> "⭐"
                                    "spa" -> "🐚"
                                    "fort" -> "🏰"
                                    "circle" -> "⚪"
                                    "local_fire_department" -> "🔥"
                                    "visibility" -> "🦉"
                                    "cottage" -> "⛺"
                                    "forest" -> "🌲"
                                    "cruelty_free" -> "🦊"
                                    "home" -> "🛖"
                                    "agriculture" -> "🚜"
                                    "toys" -> "💨"
                                    "traffic" -> "🚦"
                                    "directions_bus" -> "🚌"
                                    "pedal_bike" -> "🚲"
                                    "apartment" -> "🏢"
                                    "lightbulb" -> "🏮"
                                    "chair" -> "🪑"
                                    "rocket_launch" -> "🚀"
                                    "sports_motorsports" -> "🪖"
                                    "camera" -> "🔭"
                                    "person" -> "👨‍🚀"
                                    "settings_input_antenna" -> "🛰️"
                                    "change_history" -> "🔺"
                                    "diamond" -> "💎"
                                    "description" -> "📜"
                                    "inventory_2" -> "🪙"
                                    "theater_comedy" -> "🎭"
                                    else -> "🔍"
                                },
                                fontSize = 24.sp
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = obj.name,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp,
                                    letterSpacing = 0.5.sp
                                ),
                                color = if (isDiscovered) Color(0xFF047857) else Color.White,
                                maxLines = 1
                            )
                        }

                        // Checkmark badge when discovered
                        if (isDiscovered) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(2.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Discovered",
                                    tint = Color(0xFF10B981),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
