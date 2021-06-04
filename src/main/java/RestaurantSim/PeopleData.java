package RestaurantSim;

import java.util.List;

public class PeopleData
{
    private List<String> names;
    private List<String> surnames;

    public PeopleData()
    {

    }

    public List<String> GetNames()
    {
        return names;
    }

    public List<String> GetSurnames()
    {
        return surnames;
    }

    public String GetRandomName()
    {
        if (!names.isEmpty())
            return names.get(SimulationUitilities.GetRandomInt(names.size()));

        return "Invalid name";
    }

    public String GetRandomSurname()
    {
        if (!surnames.isEmpty())
            return surnames.get(SimulationUitilities.GetRandomInt(surnames.size()));

        return "Invalid surname";
    }

    public String GetRandomFullName()
    {
        return GetRandomName() + " " + GetRandomSurname();
    }

}
