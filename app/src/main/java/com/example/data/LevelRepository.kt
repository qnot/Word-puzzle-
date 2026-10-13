package com.example.data

import com.example.model.Level
import com.example.model.Puzzle
import com.example.model.PuzzleType
import com.example.model.SceneObject

object LevelRepository {

    val levels: List<Level> = listOf(
        // LEVEL 1: BEDROOM
        Level(
            id = 1,
            name = "Bedroom",
            subtitle = "Cozy Quarters",
            environment = "Cozy Bedroom with soft warm lighting",
            iconEmoji = "🛏️",
            wallColor = 0xFFF3E8FF,
            floorColor = 0xFFDDD6FE,
            accentColor = 0xFF7C3AED,
            skyGradientStart = 0xFFC4B5FD,
            skyGradientEnd = 0xFFDDD6FE,
            description = "Explore your cozy bedroom and discover familiar comfort items!",
            sceneObjects = listOf(
                SceneObject("bed", "BED", "Bed", 0.32f, 0.65f, 0.18f, "bed", 0xFF6366F1, 0xFF4338CA),
                SceneObject("lamp", "LAMP", "Lamp", 0.76f, 0.40f, 0.10f, "light", 0xFFF59E0B, 0xFFFEF3C7),
                SceneObject("book", "BOOK", "Book", 0.66f, 0.52f, 0.09f, "menu_book", 0xFFEC4899, 0xFFFBCFE8),
                SceneObject("clock", "CLOCK", "Clock", 0.82f, 0.22f, 0.09f, "schedule", 0xFF3B82F6, 0xFFBFDBFE),
                SceneObject("pillow", "PILLOW", "Pillow", 0.22f, 0.52f, 0.11f, "cloud", 0xFFE0E7FF, 0xFFC7D2FE),
                SceneObject("rug", "RUG", "Rug", 0.50f, 0.85f, 0.15f, "layers", 0xFFA78BFA, 0xFFDDD6FE),
                SceneObject("window", "WINDOW", "Window", 0.35f, 0.24f, 0.13f, "window", 0xFF38BDF8, 0xFFBAE6FD),
                SceneObject("teddy", "TEDDY", "Teddy Bear", 0.52f, 0.60f, 0.08f, "pets", 0xFFD97706, 0xFFFEF3C7)
            ),
            puzzles = listOf(
                Puzzle(
                    id = "l1_p1",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "You sleep on me every night.",
                    answer = "BED",
                    targetObjectId = "bed",
                    hintDetail = "Look near the left side of the room where you rest."
                ),
                Puzzle(
                    id = "l1_p2",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "I help you see when the room is dark.",
                    answer = "LAMP",
                    targetObjectId = "lamp",
                    hintDetail = "Check the nightstand with the glowing bulb."
                ),
                Puzzle(
                    id = "l1_p3",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "You read me to learn stories and adventures.",
                    answer = "BOOK",
                    targetObjectId = "book",
                    hintDetail = "It has paper pages lying open on the bedside table."
                ),
                Puzzle(
                    id = "l1_p4",
                    type = PuzzleType.MISSING_LETTERS,
                    clue = "I tick every second and tell you what time it is.",
                    answer = "CLOCK",
                    targetObjectId = "clock",
                    displayPattern = "C L _ C K",
                    missingLetter = 'O',
                    missingLetterOptions = listOf('A', 'E', 'O', 'U'),
                    hintDetail = "Hangs on the upper right wall with hands ticking."
                ),
                Puzzle(
                    id = "l1_p5",
                    type = PuzzleType.SCRAMBLED_LETTERS,
                    clue = "Soft fluffy cushion you rest your head on.",
                    answer = "PILLOW",
                    targetObjectId = "pillow",
                    scrambledLetters = listOf('W', 'O', 'L', 'L', 'I', 'P'),
                    hintDetail = "Resting right on top of the comfortable bed."
                )
            ),
            bonusWord = Puzzle(
                id = "l1_bonus",
                type = PuzzleType.FIND_OBJECT,
                clue = "Bonus Secret: Find the cute cuddly friend!",
                answer = "TEDDY",
                targetObjectId = "teddy",
                hintDetail = "A soft brown stuffed companion hiding near the mattress.",
                points = 150
            )
        ),

        // LEVEL 2: KITCHEN
        Level(
            id = 2,
            name = "Kitchen",
            subtitle = "Culinary Workshop",
            environment = "Bright Sunlit Kitchen with fresh food and appliances",
            iconEmoji = "🍳",
            wallColor = 0xFFFFFBEB,
            floorColor = 0xFFFED7AA,
            accentColor = 0xFFEA580C,
            skyGradientStart = 0xFFFDE68A,
            skyGradientEnd = 0xFFFEF3C7,
            description = "Uncover kitchen appliances, tasty snacks, and cooking utensils!",
            sceneObjects = listOf(
                SceneObject("fridge", "FRIDGE", "Fridge", 0.18f, 0.45f, 0.16f, "kitchen", 0xFF0284C7, 0xFFBAE6FD),
                SceneObject("banana", "BANANA", "Banana", 0.48f, 0.62f, 0.08f, "eco", 0xFFFACC15, 0xFFFEF08A),
                SceneObject("apple", "APPLE", "Apple", 0.60f, 0.60f, 0.08f, "nutrition", 0xFFEF4444, 0xFFFECACA),
                SceneObject("cup", "CUP", "Cup", 0.72f, 0.58f, 0.08f, "local_cafe", 0xFF06B6D4, 0xFFCFFAFE),
                SceneObject("teapot", "TEAPOT", "Teapot", 0.42f, 0.52f, 0.09f, "coffee", 0xFF8B5CF6, 0xFFEDE9FE),
                SceneObject("oven", "OVEN", "Oven", 0.84f, 0.62f, 0.13f, "microwave", 0xFF475569, 0xFF94A3B8),
                SceneObject("cookie", "COOKIE", "Cookie", 0.54f, 0.72f, 0.07f, "cake", 0xFFB45309, 0xFFFDE68A)
            ),
            puzzles = listOf(
                Puzzle(
                    id = "l2_p1",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "I keep your milk, food, and veggies cold and fresh.",
                    answer = "FRIDGE",
                    targetObjectId = "fridge",
                    hintDetail = "The tall cool appliance standing on the left."
                ),
                Puzzle(
                    id = "l2_p2",
                    type = PuzzleType.DESCRIPTION_FIND,
                    clue = "I am yellow, curved, sweet to eat, and loved by monkeys.",
                    answer = "BANANA",
                    targetObjectId = "banana",
                    hintDetail = "A yellow crescent fruit sitting on the kitchen counter."
                ),
                Puzzle(
                    id = "l2_p3",
                    type = PuzzleType.ANAGRAM,
                    clue = "A crunchy sweet fruit that can be bright red or green.",
                    answer = "APPLE",
                    targetObjectId = "apple",
                    scrambledLetters = listOf('P', 'L', 'E', 'A', 'P'),
                    hintDetail = "Red round fruit beside the banana."
                ),
                Puzzle(
                    id = "l2_p4",
                    type = PuzzleType.MISSING_LETTERS,
                    clue = "You drink water, juice, or tea from me.",
                    answer = "CUP",
                    targetObjectId = "cup",
                    displayPattern = "C _ P",
                    missingLetter = 'U',
                    missingLetterOptions = listOf('A', 'O', 'U', 'I'),
                    hintDetail = "A handy mug placed on the breakfast counter."
                ),
                Puzzle(
                    id = "l2_p5",
                    type = PuzzleType.WORD_COMBINATION,
                    clue = "Combine the warm beverage with the cooking pot!",
                    answer = "TEAPOT",
                    targetObjectId = "teapot",
                    compoundClueParts = listOf("TEA", "POT"),
                    hintDetail = "A kettle with a spout ready to pour hot tea."
                )
            ),
            bonusWord = Puzzle(
                id = "l2_bonus",
                type = PuzzleType.FIND_OBJECT,
                clue = "Bonus Secret: Find the chocolate chip treat!",
                answer = "COOKIE",
                targetObjectId = "cookie",
                hintDetail = "Freshly baked snack on the plate.",
                points = 150
            )
        ),

        // LEVEL 3: CLASSROOM
        Level(
            id = 3,
            name = "Classroom",
            subtitle = "School of Wonder",
            environment = "Cozy classroom with blackboard, books, and study desks",
            iconEmoji = "📚",
            wallColor = 0xFFF0FDF4,
            floorColor = 0xFFE2E8F0,
            accentColor = 0xFF059669,
            skyGradientStart = 0xFFBBF7D0,
            skyGradientEnd = 0xFFDCFCE7,
            description = "Explore desks, study tools, and school discoveries!",
            sceneObjects = listOf(
                SceneObject("blackboard", "BLACKBOARD", "Blackboard", 0.50f, 0.28f, 0.20f, "space_dashboard", 0xFF1E293B, 0xFF334155),
                SceneObject("backpack", "BACKPACK", "Backpack", 0.18f, 0.68f, 0.11f, "backpack", 0xFF0284C7, 0xFF38BDF8),
                SceneObject("globe", "GLOBE", "Globe", 0.78f, 0.45f, 0.11f, "public", 0xFF2563EB, 0xFF60A5FA),
                SceneObject("ruler", "RULER", "Ruler", 0.44f, 0.64f, 0.08f, "straighten", 0xFFF59E0B, 0xFFFCD34D),
                SceneObject("bell", "BELL", "Bell", 0.84f, 0.18f, 0.08f, "notifications", 0xFFEAB308, 0xFFFEF08A),
                SceneObject("pencil", "PENCIL", "Pencil", 0.56f, 0.64f, 0.07f, "edit", 0xFFEF4444, 0xFFFCA5A5)
            ),
            puzzles = listOf(
                Puzzle(
                    id = "l3_p1",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "Teachers write math formulas and words on me with chalk.",
                    answer = "BLACKBOARD",
                    targetObjectId = "blackboard",
                    hintDetail = "The large dark slate at the front of the room."
                ),
                Puzzle(
                    id = "l3_p2",
                    type = PuzzleType.WORD_COMBINATION,
                    clue = "Worn on your BACK + PACKed with school books!",
                    answer = "BACKPACK",
                    targetObjectId = "backpack",
                    compoundClueParts = listOf("BACK", "PACK"),
                    hintDetail = "Resting on the floor near the student desk."
                ),
                Puzzle(
                    id = "l3_p3",
                    type = PuzzleType.ANAGRAM,
                    clue = "A spinning miniature sphere model of our planet Earth.",
                    answer = "GLOBE",
                    targetObjectId = "globe",
                    scrambledLetters = listOf('B', 'E', 'L', 'O', 'G'),
                    hintDetail = "Spherical world map sitting on the teacher's shelf."
                ),
                Puzzle(
                    id = "l3_p4",
                    type = PuzzleType.MISSING_LETTERS,
                    clue = "You use me to measure inches and draw straight lines.",
                    answer = "RULER",
                    targetObjectId = "ruler",
                    displayPattern = "R _ L E R",
                    missingLetter = 'U',
                    missingLetterOptions = listOf('A', 'E', 'U', 'O'),
                    hintDetail = "Wooden measuring stick lying flat on the desk."
                ),
                Puzzle(
                    id = "l3_p5",
                    type = PuzzleType.SCRAMBLED_LETTERS,
                    clue = "I ring aloud when recess and class start!",
                    answer = "BELL",
                    targetObjectId = "bell",
                    scrambledLetters = listOf('L', 'E', 'L', 'B'),
                    hintDetail = "Mounted near the door frame up high."
                )
            ),
            bonusWord = Puzzle(
                id = "l3_bonus",
                type = PuzzleType.FIND_OBJECT,
                clue = "Bonus Secret: Find the sharpened yellow writing tool!",
                answer = "PENCIL",
                targetObjectId = "pencil",
                hintDetail = "Small pencil ready on the table.",
                points = 150
            )
        ),

        // LEVEL 4: GARDEN
        Level(
            id = 4,
            name = "Garden",
            subtitle = "Blossom Grove",
            environment = "Sun-drenched botanical garden filled with butterflies and blooms",
            iconEmoji = "🌸",
            wallColor = 0xFFECFDF5,
            floorColor = 0xFFD1FAE5,
            accentColor = 0xFF10B981,
            skyGradientStart = 0xFF6EE7B7,
            skyGradientEnd = 0xFFA7F3D0,
            description = "Bask in the sun, search flowers, trees, and friendly garden creatures!",
            sceneObjects = listOf(
                SceneObject("butterfly", "BUTTERFLY", "Butterfly", 0.72f, 0.32f, 0.10f, "flutter", 0xFFF43F5E, 0xFFFDA4AF),
                SceneObject("tree", "TREE", "Tree", 0.22f, 0.40f, 0.20f, "park", 0xFF15803D, 0xFF86EFAC),
                SceneObject("sunflower", "SUNFLOWER", "Sunflower", 0.50f, 0.62f, 0.12f, "wb_sunny", 0xFFEAB308, 0xFFFEF08A),
                SceneObject("can", "CAN", "Watering Can", 0.78f, 0.68f, 0.10f, "water_drop", 0xFF0284C7, 0xFFBAE6FD),
                SceneObject("bird", "BIRD", "Bird", 0.32f, 0.25f, 0.08f, "flutter", 0xFF3B82F6, 0xFF93C5FD),
                SceneObject("ladybug", "LADYBUG", "Ladybug", 0.42f, 0.74f, 0.07f, "pest_control", 0xFFDC2626, 0xFF111827)
            ),
            puzzles = listOf(
                Puzzle(
                    id = "l4_p1",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "I have wings, colorful patterns, and flutter among flowers.",
                    answer = "BUTTERFLY",
                    targetObjectId = "butterfly",
                    hintDetail = "Drifting gently near the right side of the greenery."
                ),
                Puzzle(
                    id = "l4_p2",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "Find the tall living plant that gives cool shade and leaves.",
                    answer = "TREE",
                    targetObjectId = "tree",
                    hintDetail = "Huge leafy crown shading the left of the garden."
                ),
                Puzzle(
                    id = "l4_p3",
                    type = PuzzleType.WORD_COMBINATION,
                    clue = "Combine the glowing yellow SUN with a blooming FLOWER!",
                    answer = "SUNFLOWER",
                    targetObjectId = "sunflower",
                    compoundClueParts = listOf("SUN", "FLOWER"),
                    hintDetail = "Giant cheerful yellow bloom facing the sunlight."
                ),
                Puzzle(
                    id = "l4_p4",
                    type = PuzzleType.MISSING_LETTERS,
                    clue = "Spouts rain to water thirsty plants: Watering C _ N",
                    answer = "CAN",
                    targetObjectId = "can",
                    displayPattern = "C _ N",
                    missingLetter = 'A',
                    missingLetterOptions = listOf('A', 'E', 'O', 'U'),
                    hintDetail = "Metal gardening can sitting in the grass."
                ),
                Puzzle(
                    id = "l4_p5",
                    type = PuzzleType.ANAGRAM,
                    clue = "I sing sweet morning melodies in the leafy branches.",
                    answer = "BIRD",
                    targetObjectId = "bird",
                    scrambledLetters = listOf('R', 'I', 'D', 'B'),
                    hintDetail = "Blue songbird perched on a tree branch."
                )
            ),
            bonusWord = Puzzle(
                id = "l4_bonus",
                type = PuzzleType.FIND_OBJECT,
                clue = "Bonus Secret: Find the tiny spotted red beetle!",
                answer = "LADYBUG",
                targetObjectId = "ladybug",
                hintDetail = "A little spotted bug crawling on a garden leaf.",
                points = 150
            )
        ),

        // LEVEL 5: BEACH
        Level(
            id = 5,
            name = "Beach",
            subtitle = "Sunny Shores",
            environment = "Sparkling ocean tide, golden sandy dunes, and palm trees",
            iconEmoji = "🏖️",
            wallColor = 0xFFF0F9FF,
            floorColor = 0xFFFEF3C7,
            accentColor = 0xFF0284C7,
            skyGradientStart = 0xFF38BDF8,
            skyGradientEnd = 0xFFBAE6FD,
            description = "Listen to gentle ocean waves and discover coastal wonders!",
            sceneObjects = listOf(
                SceneObject("umbrella", "UMBRELLA", "Umbrella", 0.28f, 0.42f, 0.16f, "beach_access", 0xFFEF4444, 0xFFFEE2E2),
                SceneObject("starfish", "STARFISH", "Starfish", 0.68f, 0.72f, 0.10f, "star", 0xFFF97316, 0xFFFED7AA),
                SceneObject("crab", "CRAB", "Crab", 0.42f, 0.78f, 0.09f, "pest_control", 0xFFDC2626, 0xFFFECACA),
                SceneObject("shell", "SHELL", "Shell", 0.54f, 0.70f, 0.08f, "spa", 0xFFEC4899, 0xFFFCE7F3),
                SceneObject("castle", "CASTLE", "Sandcastle", 0.82f, 0.58f, 0.12f, "fort", 0xFFD97706, 0xFFFDE68A),
                SceneObject("pearl", "PEARL", "Pearl", 0.52f, 0.82f, 0.06f, "circle", 0xFFF8FAFC, 0xFFE2E8F0)
            ),
            puzzles = listOf(
                Puzzle(
                    id = "l5_p1",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "Provides wide cool shade on the hot sunny beach.",
                    answer = "UMBRELLA",
                    targetObjectId = "umbrella",
                    hintDetail = "Striped sunshade staked firmly into the sand."
                ),
                Puzzle(
                    id = "l5_p2",
                    type = PuzzleType.WORD_COMBINATION,
                    clue = "A five-pointed night STAR + swimming ocean FISH!",
                    answer = "STARFISH",
                    targetObjectId = "starfish",
                    compoundClueParts = listOf("STAR", "FISH"),
                    hintDetail = "Orange star-shaped friend resting near the tide."
                ),
                Puzzle(
                    id = "l5_p3",
                    type = PuzzleType.SCRAMBLED_LETTERS,
                    clue = "Little red crustacean with pincers walking sideways.",
                    answer = "CRAB",
                    targetObjectId = "crab",
                    scrambledLetters = listOf('B', 'A', 'R', 'C'),
                    hintDetail = "Scuttling playfully along the shoreline."
                ),
                Puzzle(
                    id = "l5_p4",
                    type = PuzzleType.MISSING_LETTERS,
                    clue = "Spiral ocean souvenir washed onto the shore: S H _ L L",
                    answer = "SHELL",
                    targetObjectId = "shell",
                    displayPattern = "S H _ L L",
                    missingLetter = 'E',
                    missingLetterOptions = listOf('A', 'E', 'O', 'I'),
                    hintDetail = "Pretty pink seashell resting in the warm sand."
                ),
                Puzzle(
                    id = "l5_p5",
                    type = PuzzleType.ANAGRAM,
                    clue = "Sculpted towers made with buckets and moist sand.",
                    answer = "CASTLE",
                    targetObjectId = "castle",
                    scrambledLetters = listOf('T', 'A', 'C', 'S', 'E', 'L'),
                    hintDetail = "Majestic sand fortress with tiny flags on top."
                )
            ),
            bonusWord = Puzzle(
                id = "l5_bonus",
                type = PuzzleType.FIND_OBJECT,
                clue = "Bonus Secret: Find the lustrous shiny ocean pearl!",
                answer = "PEARL",
                targetObjectId = "pearl",
                hintDetail = "A glowing gem hidden near the seashell.",
                points = 150
            )
        ),

        // LEVEL 6: FOREST
        Level(
            id = 6,
            name = "Forest",
            subtitle = "Whispering Pines",
            environment = "Misty woodland trails, pine needles, campfire, and wildlife",
            iconEmoji = "🌲",
            wallColor = 0xFFECFDF5,
            floorColor = 0xFFD1FAE5,
            accentColor = 0xFF047857,
            skyGradientStart = 0xFF10B981,
            skyGradientEnd = 0xFF065F46,
            description = "Breathe fresh pine air and explore the campsite in the woods!",
            sceneObjects = listOf(
                SceneObject("campfire", "CAMPFIRE", "Campfire", 0.50f, 0.70f, 0.12f, "local_fire_department", 0xFFEA580C, 0xFFFDE047),
                SceneObject("owl", "OWL", "Owl", 0.24f, 0.30f, 0.09f, "visibility", 0xFF92400E, 0xFFFEF3C7),
                SceneObject("mushroom", "MUSHROOM", "Mushroom", 0.72f, 0.74f, 0.08f, "spa", 0xFFEF4444, 0xFFFEE2E2),
                SceneObject("tent", "TENT", "Tent", 0.78f, 0.48f, 0.14f, "cottage", 0xFF0284C7, 0xFF38BDF8),
                SceneObject("pine", "PINE", "Pine Tree", 0.18f, 0.45f, 0.16f, "forest", 0xFF166534, 0xFF86EFAC),
                SceneObject("fox", "FOX", "Fox", 0.36f, 0.78f, 0.08f, "cruelty_free", 0xFFEA580C, 0xFFFED7AA)
            ),
            puzzles = listOf(
                Puzzle(
                    id = "l6_p1",
                    type = PuzzleType.WORD_COMBINATION,
                    clue = "A wilderness CAMP + crackling warm FIRE!",
                    answer = "CAMPFIRE",
                    targetObjectId = "campfire",
                    compoundClueParts = listOf("CAMP", "FIRE"),
                    hintDetail = "Glowing logs and flames warming the campsite center."
                ),
                Puzzle(
                    id = "l6_p2",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "Wise nocturnal bird with big round eyes perching silently.",
                    answer = "OWL",
                    targetObjectId = "owl",
                    hintDetail = "Perched on a pine bough on the upper left."
                ),
                Puzzle(
                    id = "l6_p3",
                    type = PuzzleType.MISSING_LETTERS,
                    clue = "Spotted woodland fungus growing on damp moss: M U S H _ O O M",
                    answer = "MUSHROOM",
                    targetObjectId = "mushroom",
                    displayPattern = "M U S H _ O O M",
                    missingLetter = 'R',
                    missingLetterOptions = listOf('R', 'T', 'L', 'N'),
                    hintDetail = "Red capped mushroom on the forest floor."
                ),
                Puzzle(
                    id = "l6_p4",
                    type = PuzzleType.SCRAMBLED_LETTERS,
                    clue = "Canvas shelter staked down for overnight camping under stars.",
                    answer = "TENT",
                    targetObjectId = "tent",
                    scrambledLetters = listOf('N', 'E', 'T', 'T'),
                    hintDetail = "Triangular blue tent pitched on the right."
                ),
                Puzzle(
                    id = "l6_p5",
                    type = PuzzleType.ANAGRAM,
                    clue = "Evergreen woodland tree with pinecones and fragrant needles.",
                    answer = "PINE",
                    targetObjectId = "pine",
                    scrambledLetters = listOf('N', 'I', 'P', 'E'),
                    hintDetail = "Tall evergreen pine tree standing guard."
                )
            ),
            bonusWord = Puzzle(
                id = "l6_bonus",
                type = PuzzleType.FIND_OBJECT,
                clue = "Bonus Secret: Find the sneaky orange bush tail friend!",
                answer = "FOX",
                targetObjectId = "fox",
                hintDetail = "A clever red fox peering through the bushes.",
                points = 150
            )
        ),

        // LEVEL 7: FARM
        Level(
            id = 7,
            name = "Farm",
            subtitle = "Harvest Valley",
            environment = "Sun-drenched pastoral farm with barns, animals, and windmills",
            iconEmoji = "🚜",
            wallColor = 0xFFFEFCE8,
            floorColor = 0xFFFEE2E2,
            accentColor = 0xFFB91C1C,
            skyGradientStart = 0xFFF59E0B,
            skyGradientEnd = 0xFFFBBF24,
            description = "Visit farm animals, harvest crops, and explore the red barn!",
            sceneObjects = listOf(
                SceneObject("barn", "BARN", "Barn", 0.30f, 0.36f, 0.18f, "home", 0xFFDC2626, 0xFFFEE2E2),
                SceneObject("cow", "COW", "Cow", 0.65f, 0.62f, 0.12f, "pets", 0xFF475569, 0xFFF8FAFC),
                SceneObject("tractor", "TRACTOR", "Tractor", 0.28f, 0.70f, 0.14f, "agriculture", 0xFF16A34A, 0xFFBBF7D0),
                SceneObject("rooster", "ROOSTER", "Rooster", 0.52f, 0.40f, 0.08f, "flutter", 0xFFEA580C, 0xFFFDE047),
                SceneObject("windmill", "WINDMILL", "Windmill", 0.80f, 0.28f, 0.14f, "toys", 0xFF0284C7, 0xFFBAE6FD),
                SceneObject("hay", "HAY", "Hay Bale", 0.48f, 0.72f, 0.09f, "layers", 0xFFEAB308, 0xFFFEF08A)
            ),
            puzzles = listOf(
                Puzzle(
                    id = "l7_p1",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "The big red shelter where farm equipment, horses, and hay are kept.",
                    answer = "BARN",
                    targetObjectId = "barn",
                    hintDetail = "The iconic red timber building with white trim."
                ),
                Puzzle(
                    id = "l7_p2",
                    type = PuzzleType.MISSING_LETTERS,
                    clue = "Gentle grazing animal that says 'Moo' and gives milk: C _ W",
                    answer = "COW",
                    targetObjectId = "cow",
                    displayPattern = "C _ W",
                    missingLetter = 'O',
                    missingLetterOptions = listOf('A', 'E', 'O', 'U'),
                    hintDetail = "Black and white spotted cow in the meadow."
                ),
                Puzzle(
                    id = "l7_p3",
                    type = PuzzleType.ANAGRAM,
                    clue = "Heavy wheeled machine used to plow fields and harvest crops.",
                    answer = "TRACTOR",
                    targetObjectId = "tractor",
                    scrambledLetters = listOf('T', 'O', 'R', 'C', 'T', 'A', 'R'),
                    hintDetail = "Green tractor parked beside the red barn."
                ),
                Puzzle(
                    id = "l7_p4",
                    type = PuzzleType.SCRAMBLED_LETTERS,
                    clue = "Feathered alarm clock that crows 'Cock-a-doodle-doo' at sunrise!",
                    answer = "ROOSTER",
                    targetObjectId = "rooster",
                    scrambledLetters = listOf('O', 'O', 'S', 'T', 'E', 'R', 'R'),
                    hintDetail = "Proud colorful rooster perched on the wooden fence."
                ),
                Puzzle(
                    id = "l7_p5",
                    type = PuzzleType.WORD_COMBINATION,
                    clue = "Spinning breezes of WIND + grain grinding MILL!",
                    answer = "WINDMILL",
                    targetObjectId = "windmill",
                    compoundClueParts = listOf("WIND", "MILL"),
                    hintDetail = "Tall tower with giant turning sails on the right."
                )
            ),
            bonusWord = Puzzle(
                id = "l7_bonus",
                type = PuzzleType.FIND_OBJECT,
                clue = "Bonus Secret: Find the golden bale of dry grass!",
                answer = "HAY",
                targetObjectId = "hay",
                hintDetail = "Round golden hay bale rolled up in the field.",
                points = 150
            )
        ),

        // LEVEL 8: CITY
        Level(
            id = 8,
            name = "City",
            subtitle = "Metropolis Pulse",
            environment = "Bustling downtown cityscape with towers, traffic, and pedestrians",
            iconEmoji = "🏙️",
            wallColor = 0xFFF1F5F9,
            floorColor = 0xFFCBD5E1,
            accentColor = 0xFF2563EB,
            skyGradientStart = 0xFF3B82F6,
            skyGradientEnd = 0xFF1D4ED8,
            description = "Explore street crosswalks, transport vehicles, and towering high-rises!",
            sceneObjects = listOf(
                SceneObject("light", "LIGHT", "Traffic Light", 0.22f, 0.42f, 0.10f, "traffic", 0xFFEF4444, 0xFF22C55E),
                SceneObject("bus", "BUS", "City Bus", 0.48f, 0.68f, 0.16f, "directions_bus", 0xFFEAB308, 0xFFFEF08A),
                SceneObject("bicycle", "BICYCLE", "Bicycle", 0.78f, 0.72f, 0.10f, "pedal_bike", 0xFF06B6D4, 0xFFCFFAFE),
                SceneObject("skyscraper", "SKYSCRAPER", "Skyscraper", 0.52f, 0.25f, 0.18f, "apartment", 0xFF334155, 0xFF94A3B8),
                SceneObject("streetlamp", "STREETLAMP", "Streetlamp", 0.82f, 0.45f, 0.10f, "lightbulb", 0xFFF59E0B, 0xFFFEF3C7),
                SceneObject("bench", "BENCH", "Park Bench", 0.28f, 0.76f, 0.09f, "chair", 0xFF78350F, 0xFFFDE68A)
            ),
            puzzles = listOf(
                Puzzle(
                    id = "l8_p1",
                    type = PuzzleType.MISSING_LETTERS,
                    clue = "Tells drivers to stop on red and go on green: Traffic L _ G H T",
                    answer = "LIGHT",
                    targetObjectId = "light",
                    displayPattern = "L _ G H T",
                    missingLetter = 'I',
                    missingLetterOptions = listOf('I', 'E', 'A', 'O'),
                    hintDetail = "Three-colored signal pole beside the intersection."
                ),
                Puzzle(
                    id = "l8_p2",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "Large yellow transit coach driving commuters along the avenue.",
                    answer = "BUS",
                    targetObjectId = "bus",
                    hintDetail = "Big bright yellow bus cruising along the main road."
                ),
                Puzzle(
                    id = "l8_p3",
                    type = PuzzleType.ANAGRAM,
                    clue = "Eco-friendly two-wheeled ride with pedals and handlebars.",
                    answer = "BICYCLE",
                    targetObjectId = "bicycle",
                    scrambledLetters = listOf('C', 'Y', 'C', 'L', 'E', 'B', 'I'),
                    hintDetail = "Cyan commuter bike parked by the curb."
                ),
                Puzzle(
                    id = "l8_p4",
                    type = PuzzleType.WORD_COMBINATION,
                    clue = "Reaching high into the SKY + towering cloud SCRAPER!",
                    answer = "SKYSCRAPER",
                    targetObjectId = "skyscraper",
                    compoundClueParts = listOf("SKY", "SCRAPER"),
                    hintDetail = "Glass and steel tower touching the clouds."
                ),
                Puzzle(
                    id = "l8_p5",
                    type = PuzzleType.WORD_COMBINATION,
                    clue = "Sidewalk STREET + glowing night LAMP!",
                    answer = "STREETLAMP",
                    targetObjectId = "streetlamp",
                    compoundClueParts = listOf("STREET", "LAMP"),
                    hintDetail = "Tall ornate lamppost lighting the walkway."
                )
            ),
            bonusWord = Puzzle(
                id = "l8_bonus",
                type = PuzzleType.FIND_OBJECT,
                clue = "Bonus Secret: Find the comfortable wooden resting bench!",
                answer = "BENCH",
                targetObjectId = "bench",
                hintDetail = "A wooden park bench along the city sidewalk.",
                points = 150
            )
        ),

        // LEVEL 9: SPACE STATION
        Level(
            id = 9,
            name = "Space Station",
            subtitle = "Cosmic Frontier",
            environment = "Futuristic orbital station gazing into starry nebulae and planets",
            iconEmoji = "🚀",
            wallColor = 0xFF0F172A,
            floorColor = 0xFF1E293B,
            accentColor = 0xFF8B5CF6,
            skyGradientStart = 0xFF030712,
            skyGradientEnd = 0xFF312E81,
            description = "Float in zero gravity and discover celestial equipment and rockets!",
            sceneObjects = listOf(
                SceneObject("rocket", "ROCKET", "Rocket", 0.75f, 0.32f, 0.16f, "rocket_launch", 0xFFEF4444, 0xFFFEE2E2),
                SceneObject("helmet", "HELMET", "Space Helmet", 0.28f, 0.62f, 0.10f, "sports_motorsports", 0xFF38BDF8, 0xFFE0F2FE),
                SceneObject("planet", "PLANET", "Ringed Planet", 0.32f, 0.26f, 0.14f, "public", 0xFF8B5CF6, 0xFFDDD6FE),
                SceneObject("telescope", "TELESCOPE", "Telescope", 0.52f, 0.48f, 0.12f, "camera", 0xFF10B981, 0xFFA7F3D0),
                SceneObject("astronaut", "ASTRONAUT", "Astronaut", 0.58f, 0.72f, 0.12f, "person", 0xFFF8FAFC, 0xFF94A3B8),
                SceneObject("satellite", "SATELLITE", "Satellite", 0.18f, 0.44f, 0.09f, "settings_input_antenna", 0xFFF59E0B, 0xFFFEF3C7)
            ),
            puzzles = listOf(
                Puzzle(
                    id = "l9_p1",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "Powerful spacecraft blasting fire to journey across the stars.",
                    answer = "ROCKET",
                    targetObjectId = "rocket",
                    hintDetail = "Streamlined red and white rocket docked outside."
                ),
                Puzzle(
                    id = "l9_p2",
                    type = PuzzleType.MISSING_LETTERS,
                    clue = "Protective pressurized headgear worn on spacewalks: H _ L M E T",
                    answer = "HELMET",
                    targetObjectId = "helmet",
                    displayPattern = "H _ L M E T",
                    missingLetter = 'E',
                    missingLetterOptions = listOf('E', 'A', 'I', 'O'),
                    hintDetail = "Glass visor helmet floating safely on the left."
                ),
                Puzzle(
                    id = "l9_p3",
                    type = PuzzleType.ANAGRAM,
                    clue = "Massive celestial sphere orbiting a sun with sparkling rings.",
                    answer = "PLANET",
                    targetObjectId = "planet",
                    scrambledLetters = listOf('T', 'E', 'L', 'P', 'A', 'N'),
                    hintDetail = "Purple ringed world visible through the observation bay."
                ),
                Puzzle(
                    id = "l9_p4",
                    type = PuzzleType.DESCRIPTION_FIND,
                    clue = "High-powered optical lens tube used to spy on distant galaxies.",
                    answer = "TELESCOPE",
                    targetObjectId = "telescope",
                    hintDetail = "Mounted telescope pointed out into deep space."
                ),
                Puzzle(
                    id = "l9_p5",
                    type = PuzzleType.SCRAMBLED_LETTERS,
                    clue = "Intrepid spacefarer floating weightlessly in an EVA suit.",
                    answer = "ASTRONAUT",
                    targetObjectId = "astronaut",
                    scrambledLetters = listOf('T', 'R', 'O', 'N', 'A', 'U', 'T', 'A', 'S'),
                    hintDetail = "Explorer waving in a white pressurized spacesuit."
                )
            ),
            bonusWord = Puzzle(
                id = "l9_bonus",
                type = PuzzleType.FIND_OBJECT,
                clue = "Bonus Secret: Find the solar-powered communication orbiter!",
                answer = "SATELLITE",
                targetObjectId = "satellite",
                hintDetail = "Satellite with gold panels orbiting outside.",
                points = 150
            )
        ),

        // LEVEL 10: ANCIENT TEMPLE
        Level(
            id = 10,
            name = "Ancient Temple",
            subtitle = "Pharaoh's Vault",
            environment = "Mysterious stone sanctuary carved with gold hieroglyphs and relics",
            iconEmoji = "🏛️",
            wallColor = 0xFF451A03,
            floorColor = 0xFF78350F,
            accentColor = 0xFFF59E0B,
            skyGradientStart = 0xFF78350F,
            skyGradientEnd = 0xFFB45309,
            description = "Solve timeless riddles, unearth treasures, and master the quest!",
            sceneObjects = listOf(
                SceneObject("pyramid", "PYRAMID", "Pyramid", 0.50f, 0.28f, 0.18f, "change_history", 0xFFD97706, 0xFFFDE68A),
                SceneObject("torch", "TORCH", "Torch", 0.18f, 0.44f, 0.10f, "local_fire_department", 0xFFEA580C, 0xFFFEF08A),
                SceneObject("gem", "GEM", "Glowing Gem", 0.78f, 0.58f, 0.10f, "diamond", 0xFF06B6D4, 0xFF67E8F9),
                SceneObject("scroll", "SCROLL", "Ancient Scroll", 0.32f, 0.68f, 0.09f, "description", 0xFFFDE68A, 0xFFFEF3C7),
                SceneObject("treasure", "TREASURE", "Treasure Chest", 0.52f, 0.72f, 0.15f, "inventory_2", 0xFFB45309, 0xFFF59E0B),
                SceneObject("mask", "MASK", "Golden Mask", 0.78f, 0.34f, 0.10f, "theater_comedy", 0xFFEAB308, 0xFFFEF08A)
            ),
            puzzles = listOf(
                Puzzle(
                    id = "l10_p1",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "Monumental stone triangular marvel built by ancient Pharaohs.",
                    answer = "PYRAMID",
                    targetObjectId = "pyramid",
                    hintDetail = "The great golden stone pyramid centered in the background."
                ),
                Puzzle(
                    id = "l10_p2",
                    type = PuzzleType.MISSING_LETTERS,
                    clue = "Flaming wooden sconce casting dancing fire shadows: T _ R C H",
                    answer = "TORCH",
                    targetObjectId = "torch",
                    displayPattern = "T _ R C H",
                    missingLetter = 'O',
                    missingLetterOptions = listOf('A', 'E', 'O', 'U'),
                    hintDetail = "Mounted burning torch on the ancient stone pillar."
                ),
                Puzzle(
                    id = "l10_p3",
                    type = PuzzleType.FIND_OBJECT,
                    clue = "Glittering cut jewel reflecting mystical teal beams of light.",
                    answer = "GEM",
                    targetObjectId = "gem",
                    hintDetail = "Radiant turquoise diamond floating upon an altar."
                ),
                Puzzle(
                    id = "l10_p4",
                    type = PuzzleType.ANAGRAM,
                    clue = "Ancient rolled parchment parchment filled with secret hieroglyphs.",
                    answer = "SCROLL",
                    targetObjectId = "scroll",
                    scrambledLetters = listOf('L', 'O', 'R', 'S', 'C', 'L'),
                    hintDetail = "Papyrus manuscript tied with gold ribbon."
                ),
                Puzzle(
                    id = "l10_p5",
                    type = PuzzleType.SCRAMBLED_LETTERS,
                    clue = "Heavy oak chest overflowing with sparkling coins, pearls, and gold!",
                    answer = "TREASURE",
                    targetObjectId = "treasure",
                    scrambledLetters = listOf('S', 'U', 'R', 'E', 'T', 'R', 'E', 'A'),
                    hintDetail = "Open treasure chest gleaming with untold riches."
                )
            ),
            bonusWord = Puzzle(
                id = "l10_bonus",
                type = PuzzleType.FIND_OBJECT,
                clue = "Bonus Secret: Find the sacred golden burial mask!",
                answer = "MASK",
                targetObjectId = "mask",
                hintDetail = "Golden Pharaoh death mask mounted in the upper niche.",
                points = 150
            )
        )
    )

    fun getLevelById(id: Int): Level? = levels.find { it.id == id }
}
