# Retrofit 및 OkHttp를 사용한 Kotlin + Spring Boot 예시

이 예시 코드는 Kotlin 및 Spring Boot를 사용하여 Retrofit 및 OkHttp를 통해 HTTP 클라이언트를 구현하는 방법을 보여줍니다.

## 프로젝트 설정

1. **의존성 추가**: `build.gradle` 혹은 `build.gradle.kts` 파일에 Retrofit 및 OkHttp 라이브러리를 의존성으로 추가합니다.

   ```kotlin
   implementation("com.squareup.okhttp3:okhttp:4.10.0")
   implementation("com.squareup.okhttp3:logging-interceptor:4.8.0")
   implementation("com.squareup.retrofit2:retrofit:2.9.0")
   implementation("com.squareup.retrofit2:converter-jackson:2.9.0")
   ```
   
2. **Retrofit 인터페이스 생성**: Retrofit 인터페이스를 생성합니다.

   ```kotlin
   interface UserApiClient {
       @GET("/api/v1/users/{id}")
       fun getUsers(@Path("id") id: String): User
   }
   ```
   
3. **Retrofit 빈 생성**: `UserApiClient` 인터페이스를 구현하는 Retrofit 빈을 생성합니다.
   
   ```kotlin
    @Bean
    fun userApiClient(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            )
            .connectTimeout(20000L, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                JacksonConverterFactory.create(
                    jsonMapper {
                        addModule(kotlinModule())
                        addModule(JavaTimeModule())
                    },
                ),
            )
            .client(client)
            .build()
    }
   ```
   
- 참고 : 중복이 되기 쉬우므로 아래처럼 공통 로직을 빼서 사용도 가능
  
   ```kotlin
   @Bean
   fun userApiClient() = URL(USER_BASE_URL)
        .createRetrofit()
        .create(UserApiClient::class.java)
   
   @Bean
   fun weatherApiClient() = URL(WEATHER_BASE_URL)
      .createRetrofit()
      .create(WeatherApiClient::class.java)
   ```
   
   ```kotlin
   fun URL.createRetrofit(): Retrofit {
       val client = OkHttpClient.Builder()
           .addInterceptor(
               HttpLoggingInterceptor().apply {
                   level = HttpLoggingInterceptor.Level.BODY
               },
           )
           .connectTimeout(20000L, TimeUnit.SECONDS)
           .build()
       return Retrofit.Builder()
           .baseUrl(this.toString())
           .addConverterFactory(
               JacksonConverterFactory.create(
                   jsonMapper {
                       addModule(kotlinModule())
                       addModule(JavaTimeModule())
                   },
               ),
           )
           .client(client)
           .build()
   }
   ```


4. **빈 사용**: `UserApiClient` 빈을 사용합니다.
   
   ```kotlin
   @Service
   class UserService(
       val userApiClient: userApiClient,
   ) {
        fun getUsers(): GetUsersResponse {
            return userApiClient.getUsers("1").execute().body()
        }
   }
   ```

- 참고 : 중복이 되기 쉬우므로 아래처럼 공통 로직을 빼서 사용도 가능
  
  ```kotlin
   fun <T> Response<T>.getBody(): T {
     if (this.isSuccessful.not()) {
         throw RuntimeException("API call failed")
     }

     return this.body() ?: throw RuntimeException("API call failed")
  } 
  ```
  
    ```kotlin
     fun getUsers(): GetUsersResponse {
         return userApiClient.getUsers("1").execute().getBody()
     }
  ```
