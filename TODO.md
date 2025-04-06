### Core MVC Concept (Phase 1 - Console)

*   **Model:** Contains all game data (player stats, inventory, map state, time, weather, items, crops, animals, NPCs, relationships, quests) and the core game logic (rules for farming, energy consumption, crafting, relationship changes, saving/loading). It is completely independent of the user interface.
*   **View (Console):** Responsible *only* for displaying information to the standard output (console). It takes data provided by the Controller and formats it as text (e.g., printing menus, map segments, inventory lists, status messages, errors). It does not handle user input or modify game data.
*   **Controller:** Acts as the intermediary. It reads raw text commands from the console input, uses a parser to understand the command and its arguments, interacts with the Model to fetch data or request changes to the game state, and then instructs the View on what information to display back to the user.

---

### `com.yourgame.model` (Model Package)

*   **`GameState`**: [+]
    *   *Purpose:* Manages the overall state of an active game session. Coordinates saving and loading.
    *   *Attributes:* `players` (List<`Player`>), `currentTurnPlayerIndex` (int), `gameTime` (`TimeSystem`), `weather` (`WeatherSystem`), `map` (`Map`), `npcs` (List<`NPC`>), `shops` (List<`Shop`>).
    *   *Methods:* `getPlayer()`, `nextTurn()`, `saveState()`, `loadState()`.
*   **`Player`**: []
    *   *Attributes:* `username` (String), `hashedPassword` (String), `nickname` (String), `email` (String), `gender` (Enum `Gender`), `energy` (int), `maxEnergy` (int), `money` (int), `currentLocation` (`Coordinate`), `inventory` (`Inventory`), `equippedTool` (`Tool`), `skills` (Map<`SkillType`, `Skill`>), `relationships` (Map<String, `Relationship`> *NPC name or Player username -> Relationship*), `farmMapReference` (`FarmMap`), `knownCraftingRecipes` (Set<`Recipe`>), `knownCookingRecipes` (Set<`Recipe`>), `activeQuests` (List<`QuestStatus`>).
    *   *Methods:* `changeEnergy()`, `addXp()`, `canAfford()`, `addMoney()`, `deductMoney()`, `addItem()`, `removeItem()`, `hasItem()`, `learnRecipe()`, `updateQuest()`, `getCurrentSkillLevel()`.
*   **`NPC`**:
    *   *Attributes:* `name` (String), `currentLocation` (`Coordinate`), `schedule` (Map<`TimeCondition`, `Coordinate`>), `dialogue` (Map<`DialogueTrigger`, String>), `likedGifts` (Set<`Item`>), `dislikedGifts` (Set<`Item`>), `questsAvailable` (List<`Quest`>).
    *   *Methods:* `getDialogue()`, `checkGiftPreference()`, `getCurrentLocation()`.
*   **`Map`**:
    *   *Attributes:* `tiles` (2D Array or Map<`Coordinate`, `Tile`>), `buildings` (List<`Building`>), `spawnedForageables` (Map<`Coordinate`, `Item`>), `spawnedResources` (Map<`Coordinate`, `ResourceNode`>).
    *   *Methods:* `getTileAt()`, `isOccupied()`, `findPath()`, `placeObject()`, `removeObject()`, `spawnForageables()`.
*   **`FarmMap`** (extends `Map`?):
    *   *Attributes:* Reference to specific player's farm layout, maybe `Greenhouse` reference.
*   **`Tile`**:
    *   *Attributes:* `coordinate` (`Coordinate`), `terrainType` (Enum `Terrain`), `currentCrop` (`Crop`), `placedObject` (`PlaceableObject`), `isTilled` (boolean), `isWatered` (boolean).
    *   *Methods:* `plantCrop()`, `harvestCrop()`, `till()`, `water()`.
*   **`Item`** (Abstract Base Class/Interface):
    *   *Attributes:* `name` (String), `description` (String), `sellPrice` (int), `isStackable` (boolean).
    *   *Subtypes based on PDF:*
        *   **`Tool`**: `level` (int), `energyCost` (int). *Specific Tools:* `Hoe`, `Pickaxe`, `Axe`, `WateringCan`, `FishingPole`, `Scythe`, `MilkPail`, `Shears`. Methods like `canUseOn(Tile)`?
        *   **`Seed`**: `producesCrop` (`CropData`), `seasons` (List<`Season`>).
        *   **`Crop`**: `cropData` (`CropData`), `currentStage` (int), `daysInCurrentStage` (int), `quality` (Enum `Quality`), `isHarvestable()` (boolean), `grow()` (updates stage).
        *   **`Resource`**: (e.g., Wood, Stone, Coal, CopperOre, IronOre, GoldOre, IridiumOre).
        *   **`Forageable`**: (Wild Horseradish, Daffodil, etc.).
        *   **`Food`**: `energyRestored` (int), `buff` (`Buff`).
        *   **`ArtisanGood`**: (e.g., Mayonnaise, Cheese, Wine, Juice, Pickles).
        *   **`Fish`**: `difficulty` (int), `season` (List<`Season`>), `weather` (List<`Weather`>), `timeOfDay` (String).
        *   **`Fertilizer`**: `effectDescription` (String).
        *   **`QuestItem`**.
        *   **`Craftable`**: (e.g., Chest, Furnace, Scarecrow).
*   **`CropData`**:
    *   *Purpose:* Holds static definitions for crops. Loaded from config (JSON?).
    *   *Attributes:* `name` (String), `seedName` (String), `stages` (List<int> *days per stage*), `regrowDays` (int), `seasons` (List<`Season`>), `baseSellPrice` (int), `isGiantCropPossible` (boolean).
*   **`Inventory`**:
    *   *Attributes:* `items` (Map<`Item`, Integer> *or similar structure*), `capacity` (int).
    *   *Methods:* `addItem()`, `removeItem()`, `getItemCount()`, `hasItem()`, `isFull()`. *Special Slot for `TrashCan`?*
*   **`Skill`**:
    *   *Attributes:* `type` (Enum `SkillType`: FARMING, MINING, FORAGING, FISHING, COMBAT *if applicable*), `level` (int), `xp` (int), `xpToNextLevel` (int).
    *   *Methods:* `addXp()`, `checkLevelUp()`.
*   **`TimeSystem`**:
    *   *Attributes:* `hour` (int), `dayOfMonth` (int), `season` (Enum `Season`), `year` (int).
    *   *Methods:* `advanceHour()`, `advanceDay()`, `getCurrentDayOfWeek()` (String), `isNight()` (boolean), `formatTime()` (String), `formatDate()` (String).
*   **`WeatherSystem`**:
    *   *Attributes:* `currentWeather` (Enum `Weather`), `tomorrowWeather` (Enum `Weather`).
    *   *Methods:* `predictTomorrowWeather()`, `advanceToNextDay()`.
*   **`Relationship`**:
    *   *Attributes:* `targetCharacterName` (String), `friendshipPoints` (int), `friendshipLevel` (int), `hasGivenGiftToday` (boolean), `hasTalkedToday` (boolean). *For Player-Player:* `isMarried` (boolean).
*   **`Quest`**:
    *   *Purpose:* Holds static definition of a quest. Loaded from config?
    *   *Attributes:* `id` (String), `title` (String), `description` (String), `giverNpcName` (String), `requirements` (List<`QuestRequirement`>), `reward` (`QuestReward`).
*   **`QuestStatus`**:
    *   *Attributes:* `questId` (String), `isComplete` (boolean), `progress` (Map<String, Integer> *tracking requirement progress*).
*   **`Recipe`** (Crafting/Cooking):
    *   *Attributes:* `outputItem` (`Item`), `outputQuantity` (int), `ingredients` (Map<`Item`, Integer>), `requiredSkillLevel` (Map<`SkillType`, Integer>), `isCookingRecipe` (boolean).
*   **`ArtisanMachine`** (extends `PlaceableObject`?):
    *   *Attributes:* `name` (String), `processingTime` (int), `inputItemType` (Class<? extends Item>), `outputItem` (`Item`), `currentInput` (`Item`), `timeRemaining` (int), `isReady()` (boolean).
*   **`Shop`**:
    *   *Attributes:* `name` (String), `ownerNpcName` (String), `stock` (Map<`Item`, `ShopItemDetails`> *includes price, maybe limited quantity*), `openHour` (int), `closeHour` (int).
    *   *Methods:* `canPurchase()`, `getSellPriceForItem()`.
*   **`Building`**: (e.g., `Barn`, `Coop`, `House`, `Shed`, `Greenhouse`)
    *   *Attributes:* `name` (String), `cost` (Map<`Item`, Integer>), `location` (`Coordinate`), `size` (`Dimension`), `capacity` (int), `upgradeLevel` (int).
*   **`Animal`**:
    *   *Attributes:* `name` (String), `type` (Enum `AnimalType`), `friendship` (int), `mood` (int), `daysSinceFed` (int), `daysSincePetted` (int), `productReady` (`Item`), `locationBuilding` (`Building`).
    *   *Methods:* `feed()`, `pet()`, `produce()`.

---

### `com.yourgame.view` (View Package - Console Output)

*   **`ConsoleView`**:
    *   *Purpose:* Main class coordinating output to `System.out`. Might contain helper formatters.
*   **`MenuView`**:
    *   *Methods:* `displayLoginRegisterOptions()`, `displayMainMenu()`, `displayProfileMenu()`, `displayGameMenu()`, `displayShopMenu()`, `displayCraftingMenu()`, `displayCookingMenu()`, `displayAnimalMenu()`, `displayBuildMenu()`, `displayQuestLog()`, `displayTradeMenu()`.
*   **`MapView`**:
    *   *Methods:* `displayMapSegment(Map map, Coordinate center, int radius)`, `displayMapLegend()`.
*   **`PlayerStatusView`**:
    *   *Methods:* `displayEnergyBar(int current, int max)`, `displayMoney(int amount)`, `displayDateTime(TimeSystem time)`, `displayCurrentWeather(WeatherSystem weather)`, `displayEquippedTool(Tool tool)`, `displayBuffs(List<Buff> buffs)`.
*   **`InventoryView`**:
    *   *Methods:* `displayInventory(Inventory inventory)`, `displayItemTooltip(Item item)`.
*   **`FeedbackView`**:
    *   *Methods:* `showError(String message)`, `showMessage(String message)`, `promptForInput(String prompt)`.
*   **`DialogueView`**:
    *   *Methods:* `displayDialogueLine(String speaker, String line)`, `displayGiftResponse(String npcName, GiftReaction reaction)`.
*   **`QuestView`**:
    *   *Methods:* `displayActiveQuests(List<QuestStatus> quests)`, `displayQuestDetails(Quest quest, QuestStatus status)`.
*   **`TradeView`**:
    *   *Methods:* `displayOpenTrades(List<TradeOffer> offers)`, `displayTradeHistory(List<TradeRecord> history)`.
*   **`InfoView`**:
    *   *Methods:* `displayCropInfo(Tile tile)`, `displayItemDetails(Item item)`, `displayAnimalInfo(Animal animal)`, `displayRecipeDetails(Recipe recipe)`, `displayRelationshipInfo(Relationship relationship)`.

---

### `com.yourgame.controller` (Controller Package)

*   **`GameController`**:
    *   *Purpose:* Main game loop. Reads input, delegates to `CommandParser`, and then to appropriate sub-controllers. Manages current game `GameState` and active `Player`.
*   **`CommandParser`**:
    *   *Purpose:* Takes raw String input, validates syntax, and converts it into a structured `Command` object (e.g., command name + arguments map).
*   **`AuthController`**:
    *   *Methods:* `handleRegister(Command cmd)`, `handleLogin(Command cmd)`, `handleLogout(Command cmd)`, `handleForgotPassword(Command cmd)`, `handlePickQuestion(Command cmd)`, `handleAnswerQuestion(Command cmd)`. *Interacts with Player model (creation/lookup), potentially a User Persistence layer.*
*   **`MenuController`**:
    *   *Methods:* `handleMenuEnter(Command cmd)`, `handleMenuExit(Command cmd)`, `handleShowCurrentMenu(Command cmd)`. *Updates internal state about which menu/controller is active.*
*   **`PlayerProfileController`**:
    *   *Methods:* `handleChangeUsername(Command cmd)`, `handleChangeNickname(Command cmd)`, `handleChangeEmail(Command cmd)`, `handleChangePassword(Command cmd)`, `handleShowUserInfo(Command cmd)`.
*   **`MovementController`**:
    *   *Methods:* `handleWalk(Command cmd)`. *Interacts with Player (location, energy), Map (pathfinding, validity), TimeSystem (energy check).*
*   **`InteractionController`**:
    *   *Methods:* `handleTalk(Command cmd)`, `handleGift(Command cmd)`, `handleHug(Command cmd)`, `handleMeetNPC(Command cmd)`, `handleAskMarriage(Command cmd)`, `handleRespondMarriage(Command cmd)`. *Interacts with Player, NPC, Relationship models.*
*   **`InventoryController`**:
    *   *Methods:* `handleShowInventory(Command cmd)`, `handleTrashItem(Command cmd)`, `handlePlaceItem(Command cmd)`.
*   **`ToolController`**:
    *   *Methods:* `handleEquipTool(Command cmd)`, `handleUseTool(Command cmd)`, `handleUpgradeTool(Command cmd)`, `handleShowCurrentTool(Command cmd)`, `handleShowAvailableTools(Command cmd)`. *Interacts with Player, Tile, Crop, ResourceNode, Animal models depending on tool.*
*   **`FarmingController`**:
    *   *Methods:* `handlePlantSeed(Command cmd)`, `handleWaterTile(Command cmd)`, `handleHarvestCrop(Command cmd)`, `handleFertilizeTile(Command cmd)`, `handleShowPlantInfo(Command cmd)`, `handleCheckWaterLevel(Command cmd)`.
*   **`CraftingController`**:
    *   *Methods:* `handleShowCraftingRecipes(Command cmd)`, `handleCraftItem(Command cmd)`, `handleShowCraftingInfo(Command cmd)`.
*   **`CookingController`**:
    *   *Methods:* `handleShowCookingRecipes(Command cmd)`, `handlePrepareFood(Command cmd)`, `handleEatFood(Command cmd)`, `handleRefrigeratorAccess(Command cmd)`.
*   **`AnimalController`**:
    *   *Methods:* `handlePetAnimal(Command cmd)`, `handleFeedHay(Command cmd)`, `handleMilkAnimal(Command cmd)`, `handleShearAnimal(Command cmd)`, `handleCollectProduct(Command cmd)`, `handleBuildBuilding(Command cmd)`, `handleBuyAnimal(Command cmd)`, `handleSellAnimal(Command cmd)`, `handleShepherdAnimals(Command cmd)`, `handleShowAnimals(Command cmd)`.
*   **`FishingController`**:
    *   *Methods:* `handleStartFishing(Command cmd)` (*Might involve a mini-game logic loop within the controller or model*).
*   **`ArtisanController`**:
    *   *Methods:* `handleUseArtisanMachine(Command cmd)`, `handleGetArtisanProduct(Command cmd)`.
*   **`ShopController`**:
    *   *Methods:* `handleEnterShop(Building shopBuilding)`, `handleShowProducts(Command cmd)`, `handleShowAvailableProducts(Command cmd)`, `handlePurchaseItem(Command cmd)`, `handleSellItem(Command cmd)` (*Via Shipping Bin or directly? PDF implies bin*).
*   **`TradeController`**:
    *   *Methods:* `handleStartTrade(Command cmd)`, `handleCreateTradeOffer(Command cmd)`, `handleRespondToTradeOffer(Command cmd)`, `handleShowTradeList(Command cmd)`, `handleShowTradeHistory(Command cmd)`.
*   **`TimeController`**:
    *   *Methods:* `handleNextTurn(Command cmd)`, `handleShowTime(Command cmd)`, `handleShowDate(Command cmd)`, `handleShowDateTime(Command cmd)`, `handleShowDayOfWeek(Command cmd)`, `handleShowSeason(Command cmd)`. *Coordinates end-of-day/turn updates in the Model.*
*   **`WeatherController`**:
    *   *Methods:* `handleShowWeather(Command cmd)`, `handleShowForecast(Command cmd)`.
*   **`QuestController`**:
    *   *Methods:* `handleShowQuests(Command cmd)`, `handleFinishQuest(Command cmd)`.
*   **`PersistenceController`**:
    *   *Methods:* `handleSaveGame(Command cmd)` (*potentially triggered automatically or by explicit command*), `handleLoadGame(Command cmd)`, `handleExitGame(Command cmd)` (*handles saving*), `handleForceTerminate(Command cmd)`. *Interacts with GameState and JSON library/file system.*
*   **`CheatController`**:
    *   *Methods:* `handleAdvanceTime(Command cmd)`, `handleAdvanceDate(Command cmd)`, `handleSetWeather(Command cmd)`, `handleAddMoney(Command cmd)`, `handleSetEnergy(Command cmd)`, `handleAddItem(Command cmd)`, `handleSetFriendship(Command cmd)`, `handleTriggerThunder(Command cmd)`. *Directly modifies Model state.*

---

**Interaction Flow Summary (Phase 1):**

1.  User types a command string into the console.
2.  `GameController` reads the input line.
3.  `GameController` passes the string to `CommandParser`.
4.  `CommandParser` validates syntax and creates a `Command` object.
5.  `GameController` determines the appropriate sub-controller based on the current game context (e.g., active menu, in-game) and the command name.
6.  The specific `Controller` (e.g., `MovementController`) executes its handler method (`handleWalk`).
7.  The `Controller` interacts with the `Model` (e.g., checks `Map` for validity, checks/updates `Player` energy, updates `Player` location).
8.  The `Model` updates its internal state and returns any necessary data or success/failure status.
9.  The `Controller` receives the result from the `Model`.
10. The `Controller` instructs the `ConsoleView` (or specific sub-views) what to display (e.g., `FeedbackView.showError()`, `MapView.displayMapSegment()`, `PlayerStatusView.displayEnergyBar()`).
11. The `View` formats the data and prints it to the console.
12. The `GameController` loops back to wait for the next user input.

***