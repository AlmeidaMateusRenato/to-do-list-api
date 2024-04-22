package io.github.almeida.renato.todolistapi.infrastructure.security;

import io.github.almeida.renato.todolistapi.infrastructure.persistence.model.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailService")
public class AuthService implements UserDetailsService {

    private final UserDao dao;

    public AuthService(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dao.findByUsername(username)
                .map(AuthUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
