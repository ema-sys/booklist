package jp.mynavi.azurejava.booklist.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class BooklistSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/**").permitAll()          // (1) /api/以下は全てのユーザからのアクセスを許可
                .anyRequest().authenticated()                           //     それ以外は認証されたユーザのみアクセスを許可
                .and()                                                  //
                .csrf().ignoringAntMatchers("/api/**")                  // (2) /api/以下はCSRFチェックを除外
                .and()                                                  //
                .cors()                                                 // (3) CORSの設定を有効にする
                .configurationSource(this.corsConfigurationSource());   //     CORSの設定を追加
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(Arrays.asList("POST","GET"));           // (4) 許可するHTTPメソッド
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);                  // (5) すべてのHTTPヘッダを許可する
        corsConfiguration.addAllowedOrigin("http://localhost:8080");                // (6)許可するオリジン（ローカルテスト用）
        corsConfiguration.addAllowedOrigin("https://emastrage.z31.web.core.windows.net");   // (7) 許可するオリジン（Azureストレージ用）（Blob Storage用）
        corsConfiguration.addAllowedOrigin("https://brave-field-00d0fc600.azurestaticapps.net");   // (7) 許可するオリジン（Azureストレージ用）（Blob Storage用）

        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/api/**", corsConfiguration);  // (8) このフィルタの対象URL

        return corsSource;
    }
}
