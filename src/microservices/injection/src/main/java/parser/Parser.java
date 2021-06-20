package parser;

public class Parser {

    public Parser(){

    }

    /**
     * Primeste ca parametru 'text' si-l verifica
     * @param text
     * @return
     */
    public boolean parse(String text){
        String[] words = text.split(" ");
        int drop_accusation, equal_accusation, comma_accusation, plus_accusation, empty_accusation, create_accusation, alter_accusations,
            insert_accusation, update_accusation, delete_accusation;
        drop_accusation = 0;
        for(String x : words){
            if(x.equalsIgnoreCase("drop")) drop_accusation = 1;
            else if(x.equalsIgnoreCase("create")) create_accusation = 1;
            else if(x.equalsIgnoreCase("alter")) alter_accusations = 1;
            else if(x.equalsIgnoreCase("insert")) insert_accusation = 1;
            else if(x.equalsIgnoreCase("update")) update_accusation = 1;
            else if(x.equalsIgnoreCase("delete")) delete_accusation = 1;
        }
        if(drop_accusation == 1 || create_accusation == 1 || alter_accusations == 1
            insert_accusation == 1 || update_accusation == 1 || delete_accusation == 1) return false;
        equal_accusation = 0; comma_accusation = 0; plus_accusation = 0; empty_accusation = 0;
        for(int i = 0; i < words.length; ++i){
            if(words[i].equals("=")){
                if((i + 1) != words.length && (i - 1) >= 0){
                    if(words[i - 1].equalsIgnoreCase(words[i + 1]) || words[i + 1].contains(words[i - 1])) equal_accusation = 1;
                    if(i + 1 == words.length) equal_accusation = 1;
                    if(words[i + 1].endsWith("+") || words[i + 1].startsWith("+")) plus_accusation = 1;
                    if(words[i - 1].equalsIgnoreCase("or") || words[i + 1].equalsIgnoreCase("or")) empty_accusation = 1;
                    if(words[i - 1].equalsIgnoreCase("and") || words[i + 1].equalsIgnoreCase("and")) empty_accusation = 1;
                }
            }
            if(words[i].endsWith(";") && (i + 1) != words.length) comma_accusation = 1;
        }
        if(equal_accusation == 1 || comma_accusation == 1 || plus_accusation == 1 ||
                empty_accusation == 1) return false;
        return true;
    }
}
