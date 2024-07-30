import { APP_INITIALIZER, ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptors, withInterceptorsFromDi } from '@angular/common/http';
import { AuthConfig, OAuthService, OAuthStorage, provideOAuthClient } from 'angular-oauth2-oidc';
import { AuthInterceptorDI } from './oauth2.interceptor'
import { provideAnimations } from '@angular/platform-browser/animations';


export const authCodeFlowConfig: AuthConfig = {
  issuer: 'http://localhost:8081/realms/angular-apps-realm',
  tokenEndpoint: 'http://localhost:8081/realms/angular-apps-realm/protocol/openid-connect/token',
  redirectUri: window.location.origin,
  clientId: 'book-app',
  responseType: 'code',
  scope: 'openid profile',
  showDebugInformation: true,
  timeoutFactor: 0.01
}

function initializeOAuth(oauthservice: OAuthService): Promise<void> {
  return new Promise((resolve) => {
    oauthservice.configure(authCodeFlowConfig);
    oauthservice.setupAutomaticSilentRefresh();
    oauthservice.loadDiscoveryDocumentAndLogin().then(() => resolve());
  })
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideAnimations(),
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()),
    provideOAuthClient(),
    {
      provide: APP_INITIALIZER,
      useFactory: (oauthservice: OAuthService) => {
        return () => {
          initializeOAuth(oauthservice);
        }
      },
      multi: true,
      deps: [OAuthService]
    },
    { 
      provide: HTTP_INTERCEPTORS, 
      useClass: AuthInterceptorDI, 
      multi: true 
    },
    { provide: OAuthStorage, useValue: localStorage }
  ]
};
