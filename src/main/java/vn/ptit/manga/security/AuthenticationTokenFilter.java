package vn.ptit.manga.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.ptit.manga.dto.UserDTO;
import vn.ptit.manga.service.IUserService;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private IUserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenFilter.class);
	
	private String parseJWT(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJWT(request);
			if(jwt != null && jwtUtil.validateJwtToken(jwt)) {
				String username = jwtUtil.getUserNameFromJwtToken(jwt);
				UserDTO userDTO = (UserDTO) userService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDTO, null, userDTO.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		catch(Exception e) {
			logger.error("Cannot set user authentication): {}", e);
		}
		filterChain.doFilter(request, response);
	}

}
