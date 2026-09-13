# NIT3213 Final Assignment

## Android Application Development Project

This project was developed for the NIT3213 Android Application Development final assignment.

The application connects to the NIT3213 API and allows a student to log in using their student details. After a successful login, the application retrieves data from the API and displays the results on a dashboard. The user can select an item from the dashboard to view more information on the details screen.

## Features

The application contains three main screens:

### Login Screen
- Allows the user to enter a Student ID and First Name.
- Sends the login information to the authentication API using Retrofit.
- Displays an error message when login or network connection fails.
- Opens the Dashboard after successful authentication.

### Dashboard Screen
- Retrieves entity data using the keypass returned from login.
- Displays the total number of entities.
- Uses RecyclerView to display the entity list.
- Each item shows a summary of the entity.
- Users can select an item to view its full details.

### Details Screen
- Displays the information for the selected entity.
- Includes the entity description.
- Allows the user to return to the Dashboard.

## Technologies Used

- Kotlin
- Android Studio
- XML layouts
- Retrofit
- Moshi
- OkHttp
- Hilt Dependency Injection
- RecyclerView
- ViewModel and LiveData
- Kotlin Coroutines
- JUnit

## API

The application uses the NIT3213 API:

`https://nit3213apinew.onrender.com/`

The login request uses the student's ID as the username and the student's first name as the password.

The API returns a keypass after a successful login. This keypass is then used to retrieve the dashboard data.

## Project Structure

The project is separated into packages to make the code easier to understand and maintain.

- `data/model` - Data classes used by the application.
- `data/remote` - Retrofit API service.
- `data/repository` - Repository used to communicate with the API.
- `di` - Hilt dependency injection and network setup.
- `ui/login` - Login screen and LoginViewModel.
- `ui/dashboard` - Dashboard screen, ViewModel and RecyclerView adapter.
- `ui/details` - Details screen.

## Dependency Injection

Hilt is used for dependency injection in this project.

The NetworkModule provides the networking dependencies such as Retrofit, OkHttp and the API service. The repository is injected into the ViewModels instead of creating these dependencies directly inside the Activities.

## Unit Testing

Unit tests were added for important parts of the application, including the LoginViewModel and DashboardViewModel.

The tests check application behaviour such as validation, successful responses and error handling.

## How to Run the Application

1. Clone or download this repository.
2. Open the project in Android Studio.
3. Allow Gradle to finish syncing.
4. Start an Android emulator or connect an Android device.
5. Make sure the device has an internet connection.
6. Run the `app` configuration.
7. Enter the Student ID and First Name provided for the assignment.
8. Press Login to open the Dashboard.

## Network Requirement

An internet connection is required because the application retrieves its data from the NIT3213 API.

If the emulator cannot connect to the internet, check the emulator network connection before attempting to log in.

## Author

Student ID: 8065045

NIT3213 - Android Application Development
Victoria University