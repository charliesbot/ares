# Android Dojo - Multi-Module Architecture

## Project Overview

Android application supporting both **Mobile** and **WearOS** platforms using a refined multi-module architecture with core-shared business logic and presentation-only features.

## Architecture Decision

**Refined multi-module approach** with **core-shared data/domain** and **presentation-only features** was selected for this project because:

- ✅ True feature independence - no feature-to-feature dependencies
- ✅ Single source of truth for shared business logic and models
- ✅ Supports shared domain logic between mobile and WearOS
- ✅ Clean architecture with clear separation of concerns
- ✅ Perfect balance of simplicity and scalability for side projects

## Tech Stack

- **Dependency Injection**: Koin
- **Networking**: Retrofit
- **Database**: Room
- **Navigation**: Android Navigation Component
- **Platforms**: Mobile Android + WearOS

## Module Structure

```
android-dojo/
├── app/                           # Mobile Android app module
│   ├── src/main/
│   │   ├── java/com/yourpackage/
│   │   │   ├── MainActivity.kt
│   │   │   ├── MainApplication.kt
│   │   │   ├── ui/
│   │   │   │   ├── theme/         # Mobile theme configuration
│   │   │   │   ├── navigation/    # Mobile navigation setup
│   │   │   │   └── screens/       # Mobile-specific screens
│   │   │   └── utils/             # Mobile-specific utilities
│   │   ├── res/                   # Mobile resources
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── wearos/                        # WearOS app module
│   ├── src/main/
│   │   ├── java/com/yourpackage/wear/
│   │   │   ├── MainActivity.kt
│   │   │   ├── WearApplication.kt
│   │   │   ├── ui/
│   │   │   │   ├── theme/         # Wear theme configuration
│   │   │   │   ├── navigation/    # Wear navigation (Wear Compose)
│   │   │   │   └── screens/       # Wear-specific screens
│   │   │   ├── complications/     # Watch complications
│   │   │   ├── tiles/             # Watch tiles
│   │   │   └── utils/             # WearOS-specific utilities
│   │   ├── res/                   # WearOS resources
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── core/                          # Shared core modules
│   ├── common/                    # Kotlin-only utils, Result class, extensions
│   ├── data/                      # ALL repositories, DAOs, network APIs, Room/Retrofit setup
│   │   ├── local/                 # Room database, DAOs, entities
│   │   ├── remote/                # Retrofit APIs, DTOs, network layer
│   │   └── repository/            # Repository implementations
│   ├── domain/                    # ALL domain models and use cases
│   │   ├── model/                 # Business models (User, etc.)
│   │   ├── repository/            # Repository interfaces
│   │   └── usecase/               # Use cases
│   ├── ui/                        # Shared design system
│   │   ├── theme/                 # App theme, colors, typography
│   │   ├── component/             # Reusable UI components
│   │   └── util/                  # UI utilities
│   └── di/                        # Centralized Koin DI modules
└── features/                      # Presentation-only feature modules
    ├── auth/                      # Authentication feature (presentation only)
    │   ├── di/                    # AuthModule.kt - Koin module for ViewModels
    │   ├── AuthViewModel.kt       # Authentication ViewModel
    │   ├── LoginScreen.kt         # Mobile/shared login screen
    │   └── wear/                  # WearOS-specific screens (when needed)
    │       └── WearLoginScreen.kt
    ├── dashboard/                 # Dashboard feature (presentation only)
    │   ├── di/                    # DashboardModule.kt
    │   ├── DashboardViewModel.kt
    │   ├── DashboardScreen.kt
    │   └── wear/
    │       └── WearDashboardScreen.kt
    ├── profile/                   # Profile feature (presentation only)
    │   ├── di/                    # ProfileModule.kt
    │   ├── ProfileViewModel.kt
    │   ├── ProfileScreen.kt
    │   └── wear/
    │       └── WearProfileScreen.kt
    └── [feature-name]/            # Additional features follow same pattern
        ├── di/                    # Feature DI module
        ├── [Feature]ViewModel.kt  # Feature ViewModel
        ├── [Feature]Screen.kt     # Feature screens
        └── wear/                  # Platform-specific when needed
```

## Module Dependencies

### Core Modules (Shared Business Logic)

- **core/common**: Kotlin-only utilities, Result classes, extensions
- **core/data**: ALL repositories, DAOs, network APIs, Room/Retrofit setup
- **core/domain**: ALL business models (User, etc.) and use cases
- **core/ui**: Shared design system, themes, reusable components
- **core/di**: Centralized Koin modules for all dependencies

### Feature Modules (Presentation Only)

Each feature module contains **only presentation layer**:

- **di/**: Koin module for feature ViewModels
- **[Feature]ViewModel.kt**: ViewModels that use core domain/data
- **[Feature]Screen.kt**: Mobile/shared screens (can work on both platforms)
- **wear/**: WearOS-specific screens when platform optimization needed

## What is a Feature?

A **feature** is a cohesive business capability or user journey, not just a single screen. Think of it as a complete workflow that serves a specific user need.

### Feature vs Screen Examples:

#### ✅ **Good Features** (Business Capabilities)

```
features/
├── auth/                    # Complete authentication workflow
│   ├── LoginScreen.kt       # Login screen
│   ├── RegisterScreen.kt    # Registration screen
│   ├── ForgotPasswordScreen.kt # Password recovery screen
│   └── AuthViewModel.kt     # Manages entire auth state
│
├── onboarding/             # User onboarding journey
│   ├── WelcomeScreen.kt    # Welcome screen
│   ├── PermissionsScreen.kt # Request permissions
│   ├── TutorialScreen.kt   # App tutorial
│   └── OnboardingViewModel.kt
│
├── profile/                # User profile management
│   ├── ProfileScreen.kt    # View profile
│   ├── EditProfileScreen.kt # Edit profile
│   ├── SettingsScreen.kt   # User settings
│   └── ProfileViewModel.kt
│
└── workout/                # Fitness tracking capability
    ├── WorkoutListScreen.kt    # Browse workouts
    ├── WorkoutDetailScreen.kt  # Workout details
    ├── ActiveWorkoutScreen.kt  # During workout
    ├── WorkoutHistoryScreen.kt # Past workouts
    └── WorkoutViewModel.kt
```

#### ❌ **Poor Features** (Just Screens)

```
features/
├── login-screen/           # Too granular - just one screen
├── settings-screen/        # Should be part of profile feature
├── workout-list-screen/    # Should be part of workout feature
└── home-screen/           # Probably belongs in dashboard feature
```

### Feature Identification Guidelines:

**Ask Yourself:**

1. **User Goal**: What complete task is the user trying to accomplish?
2. **Business Domain**: What business capability does this represent?
3. **Data Relationships**: What related data and actions belong together?
4. **Navigation Flow**: What screens naturally flow together?

**Examples by App Type:**

#### E-commerce App Features:

- `auth` - Login, register, password recovery
- `catalog` - Browse products, search, filters
- `cart` - Add items, modify quantities, apply coupons
- `checkout` - Payment, shipping, order confirmation
- `orders` - Order history, tracking, returns
- `profile` - Account settings, addresses, preferences

#### Social Media App Features:

- `auth` - Login, register, verification
- `feed` - Timeline, posts, likes, comments
- `profile` - User profile, edit profile, settings
- `messaging` - Chat, conversations, media sharing
- `discovery` - Explore, search users, trending
- `notifications` - Activity feed, settings

#### Fitness App Features:

- `auth` - Login, register, profile setup
- `workouts` - Browse, start, track, history
- `nutrition` - Food logging, meal plans, tracking
- `progress` - Stats, charts, goals, achievements
- `social` - Friends, sharing, challenges
- `profile` - Settings, preferences, account

### Feature Benefits:

- **Cohesive Logic**: Related functionality stays together
- **Clear Ownership**: Teams can own entire business capabilities
- **Easier Testing**: Test complete user journeys
- **Better Navigation**: Natural screen transitions within features
- **Maintainable**: Changes to a workflow stay contained

### When to Split Features:

- Feature becomes too large (10+ screens)
- Distinct user groups need different features
- Different teams need to own different capabilities
- Features have minimal interaction between them

## Detailed Feature Example: E-commerce Shopping Cart

Here's a real example showing how ONE feature contains MULTIPLE Compose files and screens:

```
features/
└── cart/                           # Single "cart" feature
    ├── di/
    │   └── CartModule.kt          # DI for cart ViewModels
    ├── CartViewModel.kt           # Manages entire cart state
    ├── CartUiState.kt            # Cart UI state models
    │
    ├── screens/                   # Multiple screens in one feature
    │   ├── CartScreen.kt          # Main cart view
    │   ├── CheckoutScreen.kt      # Checkout flow
    │   ├── PaymentScreen.kt       # Payment selection
    │   ├── ShippingScreen.kt      # Shipping address
    │   └── OrderConfirmationScreen.kt # Order success
    │
    ├── components/                # Reusable components within feature
    │   ├── CartItem.kt           # Individual cart item
    │   ├── CartSummary.kt        # Price breakdown
    │   ├── PaymentMethodCard.kt  # Payment option
    │   ├── ShippingAddressCard.kt # Address selection
    │   ├── PromoCodeInput.kt     # Coupon code entry
    │   └── QuantitySelector.kt   # +/- quantity buttons
    │
    └── wear/                      # WearOS-specific screens
        ├── WearCartScreen.kt      # Simplified cart for watch
        └── WearCheckoutScreen.kt  # Basic checkout flow
```

### Why This is ONE Feature (Not 5 Features):

**✅ **Single User Journey\*\*: Complete shopping cart workflow

- View cart → Modify items → Checkout → Payment → Confirmation

**✅ **Shared State\*\*: One ViewModel manages the entire cart state

```kotlin
// CartViewModel.kt - Manages ALL cart screens
class CartViewModel(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _cartState = MutableStateFlow(CartUiState())
    val cartState = _cartState.asStateFlow()

    // Used by CartScreen.kt
    fun updateQuantity(itemId: String, quantity: Int) { ... }
    fun removeItem(itemId: String) { ... }
    fun applyPromoCode(code: String) { ... }

    // Used by CheckoutScreen.kt
    fun proceedToCheckout() { ... }
    fun selectShippingAddress(address: Address) { ... }

    // Used by PaymentScreen.kt
    fun selectPaymentMethod(method: PaymentMethod) { ... }
    fun processPayment() { ... }

    // Used by OrderConfirmationScreen.kt
    fun getOrderDetails(): Order { ... }
}
```

**✅ **Related Data\*\*: All screens work with cart/order data

- Cart items, shipping info, payment details, order confirmation

**✅ **Natural Navigation Flow\*\*: Screens flow naturally together

```kotlin
// Navigation within cart feature
CartScreen → CheckoutScreen → PaymentScreen → ShippingScreen → OrderConfirmationScreen
```

### Component Breakdown:

**Screens** (Full-page composables):

- `CartScreen` - Full cart view with items list
- `CheckoutScreen` - Order review and options
- `PaymentScreen` - Payment method selection
- `ShippingScreen` - Address selection/entry
- `OrderConfirmationScreen` - Success page

**Components** (Reusable UI pieces):

- `CartItem` - Single item row (used in CartScreen)
- `CartSummary` - Price breakdown (used in multiple screens)
- `PaymentMethodCard` - Payment option card (used in PaymentScreen)
- `PromoCodeInput` - Coupon entry (used in CartScreen)

### Why Not Split Into Separate Features?

❌ **Don't do this**:

```
features/
├── cart-view/          # Just viewing cart
├── checkout/           # Just checkout
├── payment/            # Just payment
├── shipping/           # Just shipping
└── order-confirmation/ # Just confirmation
```

**Problems with splitting**:

- **Shared State Nightmare**: How do 5 features share cart state?
- **Complex Navigation**: Hard to manage flow between features
- **Tight Coupling**: Payment feature needs cart data, checkout needs payment data
- **Poor UX**: Breaks natural user journey into artificial boundaries

### Real-World Feature Examples:

#### Social Media "Feed" Feature:

```
features/feed/
├── screens/
│   ├── FeedScreen.kt           # Main timeline
│   ├── PostDetailScreen.kt     # Single post view
│   ├── CreatePostScreen.kt     # New post creation
│   └── StoryViewerScreen.kt    # Story viewing
├── components/
│   ├── PostCard.kt             # Individual post
│   ├── StoryRing.kt           # Story preview
│   ├── CommentItem.kt         # Comment in post
│   ├── LikeButton.kt          # Like interaction
│   └── ShareDialog.kt         # Sharing options
└── FeedViewModel.kt           # Manages all feed state
```

#### Banking "Accounts" Feature:

```
features/accounts/
├── screens/
│   ├── AccountsOverviewScreen.kt  # All accounts list
│   ├── AccountDetailScreen.kt     # Single account details
│   ├── TransactionHistoryScreen.kt # Transaction list
│   ├── TransferScreen.kt          # Money transfer
│   └── TransactionDetailScreen.kt # Single transaction
├── components/
│   ├── AccountCard.kt             # Account summary card
│   ├── TransactionItem.kt         # Transaction row
│   ├── BalanceChart.kt           # Account balance graph
│   └── TransferForm.kt           # Transfer money form
└── AccountsViewModel.kt          # Manages account data
```

**Key Insight**: One feature = One business domain with multiple related screens and components working together! 🎯

### App Modules

- **app**: Main mobile Android application
- **wearos**: WearOS companion application

## Dependency Flow

```
app/wearos → features → core
Features NEVER depend on other features
All shared business logic lives in core modules
```

## Why This Structure Eliminates Feature Coupling

❌ **Old Problem**: Profile feature needs User model → depends on Auth feature
✅ **New Solution**: Profile feature gets User model from `core/domain` → no feature dependencies

## Module Dependencies Example

```kotlin
// In app/build.gradle.kts
dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:di"))
    implementation(project(":features:auth"))
    implementation(project(":features:dashboard"))
    implementation(project(":features:profile"))
}

// In wearos/build.gradle.kts
dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:di"))
    implementation(project(":features:auth"))
    // Only features needed for WearOS
}

// In features:auth/build.gradle.kts (presentation only)
dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))  // For use cases and models
    implementation(project(":core:ui"))      // For shared components
    // NO dependency on :core:data (ViewModels use domain layer)
    // NO dependency on other features
}
```

## Koin DI Structure

```kotlin
// core/di module - Data layer
val dataModule = module {
    // Network
    single<ApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Database
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    // Repositories (ALL in core/data)
    factory<UserRepository> { UserRepositoryImpl(get(), get()) }
    factory<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}

// core/di module - Domain layer
val domainModule = module {
    // Use cases (ALL in core/domain)
    factory { LoginUseCase(get()) }
    factory { GetUserProfileUseCase(get()) }
    factory { UpdateUserUseCase(get()) }
}

// features/auth/di - Presentation only
val authModule = module {
    viewModel { AuthViewModel(get()) }  // Uses LoginUseCase from core
}

// features/profile/di - Presentation only
val profileModule = module {
    viewModel { ProfileViewModel(get(), get()) }  // Uses GetUserProfileUseCase, UpdateUserUseCase
}
```

## Key Principles

1. **True Feature Independence**: Features NEVER depend on each other, only on core modules
2. **Single Source of Truth**: All business models and logic live in core modules
3. **Shared Domain Logic**: Business logic shared between mobile and WearOS via core
4. **Clean Architecture**: Clear separation - features (presentation), core (business logic)
5. **Dependency Direction**: Features → Core (domain/data), never Feature → Feature
6. **Platform-Aware UI**: UI adapts to different form factors within features
7. **Pluggable Features**: Can add/remove any feature without affecting others

## Benefits of This Refined Structure

- **True Modularity**: Features are genuinely independent and removable
- **No Feature Coupling**: Eliminates the major architectural flaw of feature interdependencies
- **Single Source of Truth**: User model, repositories live in one place (core modules)
- **Shared Code**: Common business logic reused between mobile and WearOS
- **Easy to Start**: Begin with core modules, add features incrementally
- **Maintainability**: Clear separation - core (business), features (presentation)
- **Testability**: Business logic isolated in core, UI logic isolated in features
- **Scalability**: Perfect foundation that can grow with your project

## Getting Started

1. **Start with core modules**: Build `core/common`, `core/data`, `core/domain`, `core/ui`, `core/di`
2. **Add business logic**: Put ALL models, repositories, use cases in core modules
3. **Create presentation features**: Build `features/auth` with only ViewModels and screens
4. **Build app modules**: Implement `app` and `wearos` modules that consume features
5. **Add more features**: Each new feature only contains presentation layer
6. **Platform optimization**: Add `wear/` subfolders when WearOS needs different screens

## Multi-Platform UI Strategy

- **Shared Business Logic**: All domain/data logic in core modules works for both platforms
- **Platform-Adaptive UI**: Features contain mobile-first screens + WearOS variants when needed
- **Shared ViewModels**: Business logic and UI state remain platform-agnostic in features
- **Navigation 3 Scenes**: Use scene strategies in app modules for responsive layouts
- **Progressive Enhancement**: Start with shared screens, optimize per platform as needed

## API Implementation Example

Here's how APIs fit into this architecture:

### API Definition (core/data/remote/)

```kotlin
// core/data/remote/api/AuthApi.kt
interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<TokenResponse>

    @GET("auth/profile")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UserResponse>
}

// core/data/remote/dto/AuthDto.kt
@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserResponse
)

@Serializable
data class UserResponse(
    val id: String,
    val email: String,
    val name: String,
    val avatarUrl: String?
)
```

### Repository Implementation (core/data/repository/)

```kotlin
// core/data/repository/AuthRepositoryImpl.kt
class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val userDao: UserDao,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = authApi.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!

                // Save tokens
                tokenManager.saveTokens(
                    accessToken = loginResponse.accessToken,
                    refreshToken = loginResponse.refreshToken
                )

                // Convert DTO to domain model and save to local DB
                val user = loginResponse.user.toDomainModel()
                userDao.insertUser(user.toEntity())

                Result.success(user)
            } else {
                Result.failure(Exception("Login failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return try {
            // First try local cache
            val localUser = userDao.getCurrentUser()?.toDomainModel()
            if (localUser != null) {
                Result.success(localUser)
            } else {
                // Fetch from API if not in cache
                val token = tokenManager.getAccessToken()
                if (token != null) {
                    val response = authApi.getProfile("Bearer $token")
                    if (response.isSuccessful && response.body() != null) {
                        val user = response.body()!!.toDomainModel()
                        userDao.insertUser(user.toEntity())
                        Result.success(user)
                    } else {
                        Result.success(null)
                    }
                } else {
                    Result.success(null)
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// Extension functions for mapping
private fun UserResponse.toDomainModel(): User {
    return User(
        id = this.id,
        email = this.email,
        name = this.name,
        avatarUrl = this.avatarUrl
    )
}
```

### Domain Layer (core/domain/)

```kotlin
// core/domain/repository/AuthRepository.kt
interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun logout(): Result<Unit>
    suspend fun getCurrentUser(): Result<User?>
    suspend fun refreshToken(): Result<Unit>
}

// core/domain/model/User.kt
data class User(
    val id: String,
    val email: String,
    val name: String,
    val avatarUrl: String?
)

// core/domain/usecase/LoginUseCase.kt
class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        // Add business logic here (validation, etc.)
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password cannot be empty"))
        }

        return authRepository.login(email, password)
    }
}
```

### DI Setup (core/di/)

```kotlin
// core/di/AppModules.kt - API section
val dataModule = module {
    // Network
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(get()))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                       else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://api.yourapp.com/v1/")
            .client(get())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    // API interfaces
    single<AuthApi> { get<Retrofit>().create(AuthApi::class.java) }
    single<UserApi> { get<Retrofit>().create(UserApi::class.java) }

    // Repositories
    factory<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }

    // Utilities
    single<TokenManager> { TokenManagerImpl(androidContext()) }
}
```

### Feature Usage (features/auth/)

```kotlin
// features/auth/AuthViewModel.kt
class AuthViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            loginUseCase(email, password)
                .onSuccess { user ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        user = user,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message
                    )
                }
        }
    }
}
```

## API Architecture Benefits

- **Single Source**: All API definitions live in `core/data/remote`
- **Shared Logic**: Both mobile and WearOS use the same API layer
- **Clean Separation**: DTOs in data layer, domain models in domain layer
- **Testable**: Easy to mock repositories for testing features
- **Maintainable**: API changes only affect the data layer

## Evolution Path

This refined structure allows you to:

- **Start Simple**: Begin with core modules and basic features
- **Scale Confidently**: Add new features without fear of breaking existing ones
- **Maintain Independence**: Features remain truly pluggable and removable
- **Support Team Growth**: Multiple developers can work on different features simultaneously
- **Future-Proof**: Solid architectural foundation that scales from side project to production app
