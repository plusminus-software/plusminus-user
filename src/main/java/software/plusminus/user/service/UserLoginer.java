package software.plusminus.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import software.plusminus.security.Loginer;
import software.plusminus.security.Security;
import software.plusminus.security.SecurityParameterProvider;
import software.plusminus.user.model.User;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class UserLoginer implements Loginer {
    
    private TenantWrapper tenantWrapper;
    private UserService userService;
    @Autowired(required = false)
    private List<SecurityParameterProvider> parameterProviders = Collections.emptyList();
    
    @Nullable
    @Override
    public Security login(String username, String password) {
        User user = tenantWrapper.callWithTenantIfNeeded(username,
                () -> userService.findUser(username, password));
        if (user == null) {
            return null;
        }
        return Security.builder()
                .username(user.getUsername())
                .roles(user.getRoles())
                .others(getOthers(user))
                .build();
    }
    
    private Map<String, String> getOthers(User user) {
        Map<String, String> others = new LinkedHashMap<>();
        others.put("tenant", user.getTenant());
        others.put("email", user.getEmail());
        parameterProviders.stream()
                .map(SecurityParameterProvider::provideParameter)
                .filter(Objects::nonNull)
                .forEach(e -> others.put(e.getKey(), e.getValue()));
        return Collections.unmodifiableMap(others);
    }
}
