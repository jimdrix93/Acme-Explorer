package utilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

public class AcmeExplorerHandlerInterceptor extends org.springframework.web.servlet.i18n.LocaleChangeInterceptor{
	
	@Autowired
	services.ConfigurationService configurationService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
		
		modelAndView.addObject("enterpriseLogo", this.configurationService.findLogo());
	}

	
}
