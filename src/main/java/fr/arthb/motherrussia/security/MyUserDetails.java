//package fr.arthb.motherrussia.security;
//
//import Gambler;
//import GamblerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//public class MyUserDetails implements UserDetailsService {
//
//    @Autowired
//    private GamblerRepository gamblerRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        final Gambler user = gamblerRepository.getOne(1);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User '" + username + "' not found");
//        }
//
//        return org.springframework.security.core.userdetails.User//
//                .withUsername(username)//
//                .password(user.getPassword())//
//                .authorities(user.getRoles())//
//                .accountExpired(false)//
//                .accountLocked(false)//
//                .credentialsExpired(false)//
//                .disabled(false)//
//                .build();
//    }
//
//}
