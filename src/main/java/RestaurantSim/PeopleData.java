package RestaurantSim;

import java.util.List;

public class PeopleData
{
    private List<String> names;
    private List<String> surnames;

    public PeopleData()
    {

    }

    public List<String> getNames()
    {
        return names;
    }

    public List<String> getSurnames()
    {
        return surnames;
    }

    public String getRandomName()
    {
        if (!names.isEmpty())
            return names.get(SimulationUitilities.getRandomInt(names.size()));

        return "Invalid name";
    }

    public String getRandomSurname()
    {
        if (!surnames.isEmpty())
            return surnames.get(SimulationUitilities.getRandomInt(surnames.size()));

        return "Invalid surname";
    }

    public String getRandomFullName()
    {
        return getRandomName() + " " + getRandomSurname();
    }

}
