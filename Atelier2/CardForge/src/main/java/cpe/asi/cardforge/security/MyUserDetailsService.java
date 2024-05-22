package cpe.asi.cardforge.security;

import cpe.asi.cardforge.entity.Kuser;
import cpe.asi.cardforge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Kuser kuser = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Username" + username + " does not exist"));
        GrantedAuthority authority =
                buildUserAuthority(kuser.getRole());

        return buildUserAuthentication(kuser, Collections.singleton(authority));
    }

    private User buildUserAuthentication(Kuser kUser, Set<GrantedAuthority> authorities){
        return new User(kUser.getUserName(), kUser.getPassword(), true, true, true, true, authorities);
    }

    private GrantedAuthority buildUserAuthority(String userRole){
        return new SimpleGrantedAuthority(userRole);

    }
}
