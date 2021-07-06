package top.monoliths.slight.server.kernel;

import java.util.concurrent.ConcurrentHashMap;

import top.monoliths.slight.server.utils.ResponseRule;

/**
 * to save response rules.
 *
 * @author monoliths
 */
public final class ResponseRulesMap extends ConcurrentHashMap<String, ResponseRule> {

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
