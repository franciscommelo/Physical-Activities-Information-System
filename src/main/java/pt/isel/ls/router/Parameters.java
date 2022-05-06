package pt.isel.ls.router;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Parameters {

    String stringParameters;
    HashMap<String, LinkedList<String>> parameters = new HashMap<>();

    public Parameters(String stringParameters) {
        this.stringParameters = stringParameters;
        setParameters();
    }

    /**
     * Set parameters map
     * @param parameterMap map with key value
     */
    public Parameters(Map<String, String[]> parameterMap) {
        for (String key : parameterMap.keySet()) {
            if (!parameters.containsKey(key)) {
                parameters.put(key, new LinkedList<>(Arrays.asList(parameterMap.get(key))));
            } else {
                for (String e : parameterMap.get(key)) {
                    parameters.get(key).add(e);
                }
            }
        }
    }

    /**
     * add all to an Hashmap with with a key value "Column name" and a list with all values
     */
    private void setParameters() {

        String[] param = stringParameters.split("&");
        for (String s : param) {
            String[] vals = s.split("=");
            if (!parameters.containsKey(vals[0])) {
                vals[1] = vals[1].replaceAll("\\+", " ");
                LinkedList<String> temp = new LinkedList<>();
                temp.add(vals[1]);
                parameters.put(vals[0], temp);
            } else {
                parameters.get(vals[0]).add(vals[1]);
            }
        }
    }

    /**
     * @return HashMap parameters
     */
    public HashMap<String, LinkedList<String>> getParameters() {
        return parameters;
    }

    /**
     * Set new parameters
     *
     * @param s String
     */
    public void setNewParameters(String s) {
        this.stringParameters = s;
        setParameters();
    }
}
