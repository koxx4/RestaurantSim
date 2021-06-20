package RestaurantSim.SimulationSystem;

import java.util.List;

/**
 * Class that stores data that is related to people: names and surnames.
 */
public class PeopleData
{
    private List<String> names;
    private List<String> surnames;

    public PeopleData() {

    }

    /**
     *
     * @return List of strings that contain first names
     */
    public List<String> getNames()
    {
        return names;
    }

    /**
     *
     * @return List of strings that contain surnames
     */
    public List<String> getSurnames()
    {
        return surnames;
    }

    /**
     * Chooses a random first name and returns it.
     * @return String with a random first name
     */
    public String getRandomName()
    {
        if (!names.isEmpty())
            return names.get(SimulationUtilities.getRandomInt(names.size()));

        return "Invalid name";
    }

    /**
     * Chooses a random surname and returns it.
     * @return String with a random surname
     */
    public String getRandomSurname()
    {
        if (!surnames.isEmpty())
            return surnames.get(SimulationUtilities.getRandomInt(surnames.size()));

        return "Invalid surname";
    }

    /**
     * Constructs and returns a random name that is composed of a first and a last name.
     * @return String with a full name.
     */
    public String getRandomFullName()
    {
        return getRandomName() + " " + getRandomSurname();
    }

}
