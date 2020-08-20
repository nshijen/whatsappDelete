# whatsappDelete
public class NameCheckHelper{
        HashMap<String,Integer> nameMap = new HashMap<>();
        public void insertName(String name){
            if(nameMap.containsKey(name)) {
                Integer integer = nameMap.get(name);
                nameMap.put(name,integer++);
            }
            Log.d(TAG, "insertName: "+nameMap);
        }
        public void removeName(String name){
            if(nameMap.containsKey(name)) {
                Integer integer = nameMap.get(name);
                if(integer>1){
                    nameMap.put(name,integer--);
                }else{
                    nameMap.remove(name);
                }
            }
            Log.d(TAG, "removeName: "+nameMap);
        }
        public ArrayList<String> checkMatchingNames(String name){
            ArrayList<String> matchList = new ArrayList<>();
            if(nameMap.containsKey(name)){
               matchList.add(name);
               return matchList;
            }else{
                Set<String> keyset = nameMap.keySet();
                for(String key: keyset){
                    if (key.contains(name)){
                        matchList.add(key);
                    }
                }
                return matchList;
            }
        }
    }
