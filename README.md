# NotesApplication

NotesApplication is a robust and user-friendly note-taking application written in Kotlin, developed using Android Studio. It provides a comprehensive solution for managing personal notes, combining simplicity with functionality. The application showcases the practical implementation of essential Android development principles, including UI/UX design, data persistence, and lifecycle management.

The app is designed to cater to users who need a reliable and efficient way to organize their thoughts, tasks, and ideas. It includes features like creating, reading, updating, and deleting notes, all within an intuitive interface. With a focus on usability, NotesApplication ensures that users can quickly jot down their notes and access them whenever needed.

The data persistence is handled using SQLite, allowing notes to be saved securely on the device and accessed offline. This ensures that users' data is safe and always available, even without an internet connection. The responsive design adapts seamlessly to different screen sizes, making it suitable for both phones and tablets.

Additionally, the application follows best practices in Android development by implementing the MVVM (Model-View-ViewModel) architecture. This separation of concerns makes the codebase more manageable and scalable, facilitating easier maintenance and potential future enhancements.

Overall, NotesApplication serves as an excellent example of combining essential Android development techniques to create a practical, real-world application that meets the everyday needs of its users.

![image](https://github.com/user-attachments/assets/3c9242d1-3e8e-4fad-9632-8c6fd4ee0c8d)
![image](https://github.com/user-attachments/assets/d28c84e5-b6da-4a43-8b7f-b80eacd33aee)




## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Architecture](#architecture)

## Features

- Create new notes with title and content
- View a list of all notes
- Update existing notes
- Photos/Camera functionality
- Delete notes
- Simple and intuitive user interface
- Data persistence using SQLite

## Requirements

- Android Studio
- Android device or emulator running Android 5.0 (Lollipop) or higher

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/Mileek/NotesApplication.git
    ```
2. Open the project in Android Studio.
3. Build the project.
4. Run the application on an Android device or emulator.

## Usage

1. **Create a Note**: Tap the "Add" button, enter the title and content, and save.
2. **View Notes**: Notes are listed on the main screen. Tap a note to view details.
3. **Update a Note**: Tap a note to edit its content and save the changes.
4. **Delete a Note**: Long press a note and select the delete option.

## Architecture

The application follows a simple MVVM (Model-View-ViewModel) architecture to ensure a clean separation of concerns.

- **Model**: Handles data operations using SQLite.
- **View**: Activities and layouts for displaying data.
- **ViewModel**: Manages UI-related data and lifecycle.
