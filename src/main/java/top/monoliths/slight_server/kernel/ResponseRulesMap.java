package top.monoliths.slight_server.kernel;

import java.util.concurrent.ConcurrentHashMap;

import top.monoliths.slight_server.utils.ResponseRule;

/**
 * package class
 */
public class ResponseRulesMap extends ConcurrentHashMap<String, ResponseRule> {
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("rules: \n\r");
        values().forEach(responseRule -> {
            result.append(responseRule.toString());
        });
        result.append("rules end\n\r");
        result.append("--------------------------------\n\r");
        return result.toString();
    }
}
