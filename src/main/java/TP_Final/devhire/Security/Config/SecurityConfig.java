package TP_Final.devhire.Security.Config;
import TP_Final.devhire.Security.Filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager
    authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws
            Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/company/all").hasAuthority("READ_COMPANIES")
                        .requestMatchers("/company/*").hasAuthority("FILTER_COMPANIES")
                        .requestMatchers("/company/location/*").hasAuthority("FILTER_COMPANIES")
                        .requestMatchers("/company/update").hasAuthority("UPDATE_PROFILE")
                        .requestMatchers("/company/delete").hasAuthority("LOGIC_DOWN")
                        .requestMatchers("/dev/all").hasAuthority("READ_DEVS")
                        .requestMatchers("/dev/*").hasAuthority("FILTER_DEVS")
                        .requestMatchers("/dev/update").hasAuthority("UPDATE_PROFILE")
                        .requestMatchers("/academicInfo").hasAuthority("UPDATE_ACADEMIC_INFO")
                        .requestMatchers("/dev/jobExperience/*").hasAuthority("UPDATE_JOB_EXPERIENCE")
                        .requestMatchers("/skills/*").hasAuthority("UPDATE_SKILLS")
                        .requestMatchers("/dev/update/*").hasAuthority("UPDATE_PROFILE")
                        .requestMatchers("dev/deactivate/*").hasAuthority("LOGIC_DOWN")
                        .requestMatchers("/dev/reactivate/*").hasAuthority("LOGIC_UP")
                        .requestMatchers("/publication/post").hasAuthority("CREATE_PUBLICATION")
                        .requestMatchers("/publication/all").hasAuthority("READ_PUBLICATIONS")
                        .requestMatchers("/publication/*").hasAuthority("FILTER_PUBLICATIONS")
                        .requestMatchers("/publication/update").hasAuthority("UPDATE_PUBLICATION")
                        .requestMatchers("/publication/delete/*").hasAuthority("DELETE_PUBLICATION")
                        .requestMatchers("/publication/ownPublications").hasAuthority("READ_PUBLICATIONS")
                        .requestMatchers("/comment/publication/*").hasAuthority("CREATE_COMMENT")
                        .requestMatchers("/comment/all").hasAuthority("READ_COMMENTS")
                        .requestMatchers("/comment/ownComments").hasAuthority("FILTER_COMMENTS")
                        .requestMatchers("/comment/*").hasAuthority("FILTER_COMMENTS")
                        .requestMatchers("/comment/publication/*").hasAuthority("FILTER_COMMENTS")
                        .requestMatchers("/comment/update").hasAuthority("UPDATE_COMMENT")
                        .requestMatchers("/comment/delete/*").hasAuthority("DELETE_COMMENT")
                        .requestMatchers("/like/publication/*").hasAuthority("LIKE_PUBLICATION")
                        .requestMatchers("/like/unlike/*").hasAuthority("UNLIKE_PUBLICATION")
                        .requestMatchers("/like/all").hasAuthority("READ_LIKES")
                        .requestMatchers("/like/*").hasAuthority("FILTER_LIKES")
                        .requestMatchers("/like/ownLikes").hasAuthority("FILTER_LIKES")
                        .requestMatchers("/like/publication/*").hasAuthority("FILTER_LIKES")
                        .requestMatchers("/follow/save").hasAuthority("FOLLOW")
                        .requestMatchers("/follow/*/*/*").hasAuthority("FILTER_FOLLOWS")
                        .requestMatchers("/follow/all").hasAuthority("READ_FOLLOWS")
                        .requestMatchers("/follow/deactivate").hasAuthority("DEACTIVATE_FOLLOW")
                        .requestMatchers("/follow/reactivate").hasAuthority("REACTIVATE_FOLLOW")
                        .requestMatchers("/follow/followers/*/*").hasAuthority("READ_FOLLOWERS")
                        .requestMatchers("/follow/followings/*/*").hasAuthority("READ_FOLLOWINGS")
                        .requestMatchers("/follow/ownFollowers").hasAuthority("READ_FOLLOWERS")
                        .requestMatchers("/follow/ownFollowings").hasAuthority("READ_FOLLOWINGS")
                        .requestMatchers("/follow/delete").hasAuthority("UNFOLLOW")
                        .requestMatchers("/job/post").hasAuthority("CREATE_JOB")
                        .requestMatchers("/job/all").hasAuthority("READ_JOBS")
                        .requestMatchers("/job/*").hasAuthority("FILTER_JOBS")
                        .requestMatchers("/job/*/requirements").hasAuthority("READ_JOB_REQUIREMENTS")
                        .requestMatchers("/job/ownOffers").hasAuthority("FILTER_JOBS")
                        .requestMatchers("/job/*/findAvailableSkills").hasAuthority("READ_AVAILABLE_SKILLS")
                        .requestMatchers("/job/*/addRequirement").hasAuthority("ADD_JOB_REQUIREMENT")
                        .requestMatchers("/job/findBySkill/*").hasAuthority("FILTER_JOBS")
                        .requestMatchers("/job/findByCompany/*").hasAuthority("FILTER_JOBS")
                        .requestMatchers("/job/update/*").hasAuthority("UPDATE_JOB")
                        .requestMatchers("/job/delete/*").hasAuthority("DELETE_JOB")
                        .requestMatchers("/job/*/apply").hasAuthority("APPLY_TO_JOB")
                        .requestMatchers("/application/all").hasAuthority("READ_APPLICATIONS")
                        .requestMatchers("/application/ownApplications").hasAuthority("READ_OWN_APPLICATIONS")
                        .requestMatchers("/application/applicantsByJob/*").hasAuthority("READ_APPLICANTS")
                        .requestMatchers("/application/delete/*").hasAuthority("DELETE_APPLICATION")
                        .requestMatchers("/application/applicantsWithAllHardRequirements/*").hasAuthority("FILTER_APPLICANTS")
                        .requestMatchers("/application/applicantsWithAnyHardRequirement/*").hasAuthority("FILTER_APPLICANTS")
                        .requestMatchers("/application/applicantsWithMinHardRequirement/*").hasAuthority("FILTER_APPLICANTS")
                        .requestMatchers("/application/job/*/discardApplicant/*").hasAuthority("DISCARD_APPLICANT")
                        .requestMatchers("/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated())
                .cors(Customizer.withDefaults())
                .csrf(
                        AbstractHttpConfigurer::disable)
                .headers(headers ->headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .sessionManagement(manager ->
                        manager.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
