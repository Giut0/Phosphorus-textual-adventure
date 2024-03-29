package di.uniba.map;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import di.uniba.map.type.Enemy;
import di.uniba.map.type.Item;
import di.uniba.map.type.KeyItem;
import di.uniba.map.type.Room;
import di.uniba.map.type.Weapon;
import di.uniba.map.type.Action;
import di.uniba.map.type.ActionType;
import di.uniba.map.type.Character;

@SuppressWarnings("unchecked")

/**
 * Utility class for data manipulation.
 */
public class Utils {

    /**
     * Reads a JSON file and converts it into a map.
     *
     * @param filePath The path of the JSON file.
     * @return A map representing the JSON file.
     * @throws StreamReadException If there is a problem reading the data stream.
     * @throws DatabindException   If there is a problem binding the data.
     * @throws IOException         If there is an I/O problem.
     */
    public static Map<String, Object> readJSON(String filePath)
            throws StreamReadException, DatabindException, IOException {
        File JSON_SOURCE = new File(filePath);
        Map<String, Object> jsonFile = new ObjectMapper().readValue(JSON_SOURCE, HashMap.class);

        return jsonFile;
    }

    /**
     * Loads a list of strings from a file into a set.
     *
     * @param file The file to read from.
     * @return A set containing the strings from the file.
     * @throws IOException If there is an I/O problem.
     */
    public static Set<String> loadFileListInSet(File file) throws IOException {
        Set<String> set = new HashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (reader.ready()) {
            set.add(reader.readLine().trim().toLowerCase());
        }
        reader.close();
        return set;
    }

    /**
     * Parses a string into tokens, excluding stopwords.
     *
     * @param string    The string to parse.
     * @param stopwords The set of stopwords to exclude.
     * @return A list of tokens from the string, excluding stopwords.
     */
    public static List<String> parseString(String string, Set<String> stopwords) {
        List<String> tokens = new ArrayList<>();
        String[] split = string.toLowerCase().split("\\s+|\'");
        for (String t : split) {
            if (!stopwords.contains(t)) {
                tokens.add(t);
            }
        }
        return tokens;
    }

    /**
     * Initializes a list of actions for a game.
     *
     * Each action has an associated ActionType, a name, and an array of aliases.
     * The aliases are alternative commands that can trigger the action.
     *
     * @return A list of initialized actions.
     */
    public static List<Action> initializeActions() {

        List<Action> actions = new ArrayList<>();

        Action north = new Action(ActionType.NORTH, "nord");
        north.setActionAlias(new String[] { "n", "N", "Nord", "NORD" });
        actions.add(north);

        Action south = new Action(ActionType.SOUTH, "sud");
        south.setActionAlias(new String[] { "s", "S", "Sud", "SUD" });
        actions.add(south);

        Action east = new Action(ActionType.EAST, "est");
        east.setActionAlias(new String[] { "e", "E", "Est", "EST" });
        actions.add(east);

        Action west = new Action(ActionType.WEST, "ovest");
        west.setActionAlias(new String[] { "o", "O", "Ovest", "OVEST" });
        actions.add(west);

        Action end = new Action(ActionType.EXIT, "end");
        end.setActionAlias(
                new String[] { "end", "fine", "esci", "muori", "ammazzati", "ucciditi", "suicidati", "exit" });
        actions.add(end);

        Action look = new Action(ActionType.WATCH, "osserva");
        look.setActionAlias(new String[] { "guarda", "vedi", "trova", "cerca", "descrivi", "inspeziona", "ispezionare",
                "scruta", "scrutare" });
        actions.add(look);

        Action pickup = new Action(ActionType.PICKUP, "raccogli");
        pickup.setActionAlias(new String[] { "prendi" });
        actions.add(pickup);

        Action talk = new Action(ActionType.TALK, "parla");
        talk.setActionAlias(new String[] { "parla", "chiedi", "conversa" });
        actions.add(talk);

        Action inventory = new Action(ActionType.INVENTORY, "inventario");
        inventory.setActionAlias(new String[] { "zaino", "oggeti", "items", "inv" });
        actions.add(inventory);

        Action shoot = new Action(ActionType.SHOOT, "spara");
        shoot.setActionAlias(new String[] { "uccidi", "ammazza", "elimina", "termina", "fredda" });
        actions.add(shoot);

        Action start = new Action(ActionType.START, "inizia");
        start.setActionAlias(new String[] { "start", "comincia", "begin", "inizio", "i", "s" });
        actions.add(start);

        Action resume = new Action(ActionType.RESUME, "continua");
        resume.setActionAlias(new String[] { "resume", "riprendi" });
        actions.add(resume);

        Action music = new Action(ActionType.MUSIC, "musica");
        music.setActionAlias(new String[] { "music", "canzone", "musichetta" });
        actions.add(music);

        Action map_ground = new Action(ActionType.MAP, "mappa");
        map_ground.setActionAlias(new String[] { "piantina" });
        actions.add(map_ground);

        Action commands_help = new Action(ActionType.COMMAND_LIST, "comandi");
        commands_help.setActionAlias(new String[] { "commands", "help", "aiuto", "h", "c" });
        actions.add(commands_help);

        Action menu = new Action(ActionType.MENU, "menu");
        menu.setActionAlias(new String[] { "m" });
        actions.add(menu);

        Action save = new Action(ActionType.SAVE, "salva");
        save.setActionAlias(new String[] { "save", "salvare", "salvataggio" });
        actions.add(save);

        Action probe = new Action(ActionType.PROBE, "sonda");
        probe.setActionAlias(new String[] { "probe" });
        actions.add(probe);

        return actions;
    }

    /**
     * Initializes a list of rooms for a game from a JSON file.
     *
     * Each room has an associated ID, name, description, look description, floor
     * number, and oxygen status.
     * The method also sets adjacent rooms, password requirement, items, and
     * characters for each room.
     *
     * @return A list of initialized rooms.
     * @throws StreamReadException If there is a problem reading the data stream.
     * @throws DatabindException   If there is a problem binding the data.
     * @throws IOException         If there is an I/O problem.
     */
    public static List<Room> initializeRooms() throws StreamReadException, DatabindException, IOException {

        Map<String, Object> roomsFile = Utils.readJSON("resources/rooms.json");
        List<Room> rooms = new ArrayList<>();
        List<Item> items = initializeItems();
        List<Character> characters = initializeCharacters();

        if (roomsFile != null) {

            for (int i = 0; i < roomsFile.size(); i++) {
                Map<?, ?> result = (Map<?, ?>) roomsFile.get(Integer.toString(i));

                Room room = new Room((int) result.get("roomId"), (String) result.get("roomName"),
                        (String) result.get("roomDescription"), (String) result.get("lookDescription"),
                        (int) result.get("floorNumber"), (boolean) result.get("oxygen"));

                room.setAdjacentRooms((Integer) result.get("north"), (Integer) result.get("south"),
                        (Integer) result.get("est"), (Integer) result.get("west"));

                room.setPasswordRequired((boolean) result.get("passwordRequired"));
                List<Integer> charactersIDs = (List<Integer>) result.get("characters");
                List<Integer> roomItemsIDs = (List<Integer>) result.get("items");

                for (Integer itemID : roomItemsIDs) {
                    room.addItem(items.get(itemID));
                }

                for (Integer characterID : charactersIDs) {
                    room.addRoomCharacter(characters.get(characterID));
                }

                if ((boolean) result.get("locked")) {
                    room.setLocked(true);
                }
                rooms.add(room);
            }
        }
        return rooms;
    }

    /**
     * Initializes a list of items for a game from a JSON file.
     *
     * Each item has an associated ID, name, description, and location.
     * The method also sets item aliases for each item.
     *
     * @return A list of initialized items.
     * @throws StreamReadException If there is a problem reading the data stream.
     * @throws DatabindException   If there is a problem binding the data.
     * @throws IOException         If there is an I/O problem.
     */
    public static List<Item> initializeItems() throws StreamReadException, DatabindException, IOException {

        Map<String, Object> itemsFile = Utils.readJSON("resources/items.json");
        List<Item> items = new ArrayList<>();

        if (itemsFile != null) {

            for (int i = 0; i < itemsFile.size(); i++) {
                Map<?, ?> result = (Map<?, ?>) itemsFile.get(Integer.toString(i));

                if (((String) result.get("itemType")).equals("Weapon")) {
                    Item item = new Weapon((int) result.get("itemID"), ((String) result.get("itemName")).toLowerCase(),
                            ((String) result.get("itemDescription")).toLowerCase(), (int) result.get("itemLocation"));
                    item.setItemAlias(new HashSet<>((List<String>) result.get("itemAlias")));
                    items.add(item);
                } else {
                    Item item = new KeyItem((int) result.get("itemID"), ((String) result.get("itemName")).toLowerCase(),
                            ((String) result.get("itemDescription")).toLowerCase(), (int) result.get("itemLocation"));
                    item.setItemAlias(new HashSet<>((List<String>) result.get("itemAlias")));
                    items.add(item);
                }
            }
        }
        return items;
    }

    /**
     * Initializes a list of characters for a game from a JSON file.
     *
     * Each character has an associated ID and name.
     * The method also sets main dialog and default dialog for each character.
     *
     * @return A list of initialized characters.
     * @throws StreamReadException If there is a problem reading the data stream.
     * @throws DatabindException   If there is a problem binding the data.
     * @throws IOException         If there is an I/O problem.
     */
    private static List<Character> initializeCharacters() throws StreamReadException, DatabindException, IOException {
        Map<String, Object> charactersFile = Utils.readJSON("resources/characters.json");
        List<Character> characters = new ArrayList<>();

        if (charactersFile != null) {

            for (int i = 0; i < charactersFile.size(); i++) {
                Map<?, ?> result = (Map<?, ?>) charactersFile.get(Integer.toString(i));
                if (((String) result.get("type")).equals("enemy")) {
                    Enemy enemy = new Enemy((int) result.get("characterId"), (String) result.get("characterName"));

                    enemy.setMainDialog((String) result.get("mainDialog"));
                    enemy.setDefaultDialog((String) result.get("defaultDialog"));

                    characters.add(enemy);
                } else {
                    Character character = new Character((int) result.get("characterId"),
                            (String) result.get("characterName"));
                    character.setMainDialog((String) result.get("mainDialog"));
                    character.setDefaultDialog((String) result.get("defaultDialog"));

                    characters.add(character);
                }
            }
        }
        return characters;
    }

}
