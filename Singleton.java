public class Singleton
{
    private static Talent talentData;
    private static Tree treeData;
    private static boolean modified;

    public Singleton(){}

    public static void setData(Talent talent, Tree tree)
    {
        talentData = talent;
        treeData = tree;
        modified = false;
    }

    public static void updateTalent(Talent talent)
    {
        talentData = talent;
        modified = true;
    }

    public static Talent getTalentData()
    {
        return talentData;
    }

    public static Tree getTreeData()
    {
        return treeData;
    }

    public static boolean isModified()
    {
        return modified;
    }
}
