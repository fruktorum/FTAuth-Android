[![](https://jitpack.io/v/fruktorum/FTAuth-Android.svg)](https://jitpack.io/#fruktorum/FTAuth-Android)

<img src="https://i.gyazo.com/fcbc3c4c7369d613fa7bfef1cae5ec3a.png" align="center" width="500" >

# FTAuth Android SDK 

## Особенности

- Авторизация и регистрация пользователей
- Возможность авторизации через Facebook, Google и Apple
- Настройка полей обязательных для регистрации
- Готовые классы UI объектов

***

## Установка

Для того, чтобы добавить FTAuth Android SDK в проект, нужно добавить зависимость в ваш build.gradle файл.
```kotlin
dependencies {
    //FTAuth
    implementation("com.github.fruktorum:FTAuth-Android:1.0.0")
}
```

## Первоначальная настройка SDK

Перед началом работы необходимо проинициализировать библиотеку. Лучше всего это делать в классе ***Application***.
С помощью метода ***setServerUrl*** нужно установить URL для обращения к серверу.

```kotlin
class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        FTAuth.Companion.Builder(this).setServerUrl("Г").build()
    }
}
```

## Подключение UI элементов

### InputField

**1. Описание полей:**

| Тип InputField | Описание |
| ------ | ------ |
| **Авторизация** ||
| FTAuthEmailInputField | Для ввода **email** при авторизации |
| FTAuthPasswordInputField | Для ввода **пароля** при авторизации | 
| **Регистрация** ||
| FTRegistrationNameInputField | Для ввода **имени в произвольном формате** (в одну строку) при регистрации |
| FTRegistrationFirstNameInputField | Для ввода **имени** при регистрации |
| FTRegistrationLastNameInputField | Для ввода **фамилии** при регистрации |
| FTRegistrationEmailInputField | Для ввода **email** при регистрации |
| FTRegistrationPasswordInputField | Для ввода **пароля** при регистрации |
| FTRegistrationConfirmPasswordInputField | Для **подтверждения пароля** при регистрации |
| FTRegistrationPhoneNumberInputField | Для ввода **номера телефона** при регистрации |

**2. Подключение полей ввода**

Для подключения поля ввода нужно просто добавить его в **xml** файл разметки.

```xml
 <com.fruktorum.ftauth.customUI.auth.FTAuthEmailInputField
        android:id="@+id/email_input_field"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />
```

**3. Особенности полей ввода элементов и примеры использования:**

Каждый ***InputField*** имеет публичные переменные ***inputField*** и ***description*** - метки, для ввода значений и для вывода ошибок валидации или ошибок с сервера соответсвенно. Их свойства можно переопределять, обращаясь к ним напрямую.

**Пример:**

```kotlin
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        emailInputField.inputField.hint = "Hint"
        emailInputField.description.setTextColor(Color.BLACK) 
    }
```

Также каждый ***InputField*** имеет публичные методы ***setInputFieldStyle*** и ***
setDescriptionStyle***, которые повзоляют применить стиль для полей ввода и вывода ошибок.

**Пример:**

```kotlin
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    emailInputField.setInputFieldStyle(R.style.InputField)
    emailInputField.setDescriptionStyle(R.style.Description)
}
```

Есть возможность установить стили для полей ввода и вывода ошибок напрямую в xml разметке при помощи
аттрибутов ***inputFieldStyle*** и ***descriptionStyle***.

```xml

<com.fruktorum.ftauth.customUI.auth.FTAuthEmailInputField 
        android:id="@+id/email_input_field"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:inputFieldStyle="@style/InputField"
        app:descriptionStyle="@style/DescriptionStyle" />
```

- В классе ***FTRegistrationPhoneNumberInputField*** существует публичное свойство ***phoneMask***
  типа ***PhoneMask***, предназначеное для назначения маски ввода телефонного номера.

| Значение ***PhoneMask*** | Описание | Вид маски | Валидация |
| ------ | ------ | ------ | ------ |
| **
.X_XXX_XXX_XXXX** | Маска для номеров международного формата с кодом страны из одной цифры | "+X (XXX) XXX-XXXX" | Согласно маске |
| **
.XX_XXX_XXX_XXXX** | Маска для номеров международного формата с кодом страны из двух цифр | "+XX (XXX) XXX-XXXX" | Согласно маске |
| **.CustomMask(mask:
String)** | Пользовательская маска телефонного номера. Маска должна быть задана с учетом последовательной замены всех символов *"
X"* введенными пользователем цифрами, остальные символы маски сохранят своё положение | *
mask* | Согласно маске |
| **.NONE** | Ввод телефонного номера без маски | "" | Без валидации |

Обращение к свойству ***phoneMask*** происходит через экземпляр ***FTRegistrationPhoneNumberInputField***.

**Пример:**

```kotlin
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        phoneNumberInputField.phoneMask = PhoneMask.XX_XXX_XXX_XXXX
    }
```

- В классе ***FTRegistrationPhoneNumberInputField*** существует публичное свойство ***prefix*** типа ***String***, предназначеное для добавления специальных символов перед введенными числами номера до отправки на сервер. Значение в свойстве ***prefix*** не учитывается при валидации. Обращение к свойству ***prefix*** происходит через экземпляр ***FTRegistrationPhoneTextField***.

**Пример:**

```kotlin
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        phoneNumberInputField.prefix = "+7"
    }
```

### Button

**1. Описание:**

| Тип Button | Описание |
| ------ | ------ |
| **Авторизация по email** |
| FTLoginButton | По нажатию запускается процесс авторизации |
| **Авторизация через сторонние аккаунты** |
| FTLoginFacebookButton | По нажатию запускается авторизация через Facebook | 
| FTLoginGoogleButton | По нажатию запускается авторизация через Google | 
| **Регистрация** |
| FTRegistrationButton | По нажатию запускается процесс регистрации |
| **Выход из аккаунта** |
| FTLogoutButton | По нажатию запускается процесс выхода из аккаунта | 
| **Дополнительные кнопки** |
| FTCheckBoxAcceptanceOfTerms | Для подтверждения согласия пользователя с условиями использования приложения или любыми другими условиями без согласия на которые регистрация невозможна |

**2. Подключение кнопок**

Для подключения кнопки нужно просто добавить его в **xml** файл разметки.

```xml
 <com.fruktorum.ftauth.customUI.auth.FTLoginButton
         android:id="@+id/btn_login"
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         />
```

**3. Особенности FTButton элементов и примеры использования:**

| ***FTLoginGoogleButton*** и ***FTLoginFacebookButton*** | 
| ------ |
| По нажатию на кнопки ***FTLoginFacebookButton*** и  ***
FTLoginGoogleButton*** открывается авторизация в соответвующем в WebView. |
| Для отмены можно просто нажать на кнопку 'Back' или смахнуть этот экран вправо. |
| После авторизации пользователя в этом дополнительном окне, процесс авторизации на сервере произойдет автоматически. |

## Дополнительные настройки FTAuth

**1. FTAuth имеет следующие публичные элементы:**

| Публичный элемент FTAuth | Описание |
| ------ | ------ |
| **Переменные** |
| **requiredElements
= [TypeElement]** | Список обязательный элементов для регистрации. Запрос на регистрацию не будет отправлен, если указанные в списке поля не будут заполнены валидными данными |
| **additionalRegistrationPayload: HashMap<String, Any>
?** | Список дополнительных данных, которые необходимо передать на сервер в запросе регистрации |
| **Функции** |
| **setServerUrl(serverUrl: String)** | Устанавка URL сервера авторизации |
| **getSessionToken(): String?** | Возвращает текущий **session-token** |
| **getProviderToken(): String?** | Возвращает текущий **provider-token** |
| **setProviderToken(_ providerToken: String)** | Устанавка значения текущего **
session-token**, если он был получен не через ***FTAuth*** |
| **setSessionToken(_ sessionToken: String)** | Устанавка значения текущего **
provider-token**, если он был получен не через ***FTAuth*** |
| **Коллбэки** |
| **onLoginSuccess (() -> Unit?)?** | Срабатывает при успешном ответе запроса авторизации |
| **onLoginFailure: ((Throwable) -> Unit?)
?** | Срабатывает при ошибке запроса авторизации. Содержит в себе объект типа Throwable |
| **onRegistrationSuccess (() -> Unit?)?** | Срабатывает при успешном ответе запроса регистрации |
| **onRegistrationFailure: ((Throwable) -> Unit?)
?** | Срабатывает при ошибке запроса авторизации. Содержит в себе объект типа Throwable |
| **onLogOutSuccess (() -> Unit?)?** | Срабатывает при успешном ответе запроса логаута |
| **onLogOutFailure: ((Throwable) -> Unit?)
?** | Срабатывает при ошибке запроса логаута. Содержит в себе объект типа Throwable |

**2. Определение списка элементов, являющихся обязательными для процесса регистрации (
requiredElements):**

| Поле(тип) | Описание |
| ------ | ------ |
| ***requiredElements*** | Является массивом элементов типа ***TypeElement*** |
| ***TypeElement*** | Перечисление всех типов полей для регистрации доступных в ***
FTAuth***. Может принимать следующие значения: *NAME*, *FIRST_NAME*, *LAST_NAME*, *EMAIL*, *
PASSWORD*, *CONFIRM_PASSWORD*,*PHONE*, *
ACCEPT*). Обязательными могут быть все поля или только несколько полей. |

**Пример использования requiredElements в случае, когда все используемые поля являются
обязательными:**

```kotlin
    FTAuth.getInstance().requiredElements = listOf(
  TypeElement.EMAIL,
  TypeElement.PASSWORD,
  TypeElement.CONFIRM_PASSWORD,
  TypeElement.NAME,
  TypeElement.LAST_NAME,
  TypeElement.FIRST_NAME,
  TypeElement.PHONE,
  TypeElement.ACCEPT
)
```

**3. Получение и установка токенов (getSessionToken, getProviderToken, setSessionToken, setProviderToken)**

- Функция **getSessionToken(): String** возвращает строку с **session_token**, если он уже получен или пустую строку, если **session_token** не получен.

```kotlin
val sessionToken = FTAuth.getInstance().getSessionToken()
```

- Функция **getProviderToken(): String** возвращает строку с **provider_token**, если он уже получен или пустую строку, если **provider_token** не получен.

```kotlin
val providerToken = FTAuth.getInstance().getProviderToken()
```

- Функция **setSessionToken(sessionToken: String)** позволяет установить **session_token***, если они был получен за пределами ***FTAuth***.

```kotlin
FTAuth.getInstance().setSessionToken(sessionToken)
```

- Функция **setProviderToken(providerToken: String)** позволяет установить **provider_token***, если они был получен за пределами ***FTAuth***.

```kotlin
FTAuth.getInstance().setProviderToken(providerToken)
```











