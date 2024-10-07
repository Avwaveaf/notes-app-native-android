# Note Taking App

## Objective
The *Note Taking App* is a simple yet powerful mobile application for managing and organizing notes. The goal of this project was to build an efficient note-taking app following the MVVM architecture, leveraging Kotlin Coroutines, LiveData, and Room for local data persistence. It includes a search functionality to help users quickly find specific notes, enhancing the overall user experience.

### Skills Learned

- Proficient use of the MVVM architectural pattern for clean and maintainable code.
- Mastery of Kotlin Coroutines for handling asynchronous tasks efficiently.
- Effective use of LiveData for data-binding and UI updates.
- Experience with Room database for local data storage and persistence.
- Implementation of a SearchView for dynamic filtering of notes.
- UI/UX improvements with smooth and user-friendly search functionality.

### Tools Used

- **Kotlin** for writing clean and concise code.
- **Android Jetpack (ViewModel, LiveData, Room)** for implementing MVVM, data-binding, and local data storage.
- **Kotlin Coroutines** for asynchronous data handling.
- **RecyclerView** for displaying dynamic lists of notes.
- **SearchView** for implementing search functionality, allowing users to filter notes dynamically.
- **Room Database** for local storage and persistence of note data.

## Features

1. **Note Management:** Users can create, update, and delete notes, with all changes being dynamically reflected in the UI.
2. **MVVM Architecture:** The app follows the MVVM pattern, ensuring a clean separation between UI, data handling, and business logic.
3. **Coroutines and LiveData:** Asynchronous task handling with Kotlin Coroutines and LiveData ensures smooth and responsive UI updates.
4. **Search Functionality:** A SearchView allows users to search for notes by title or content, providing an efficient way to navigate through stored notes.
5. **Local Data Persistence:** Room database is used for storing notes locally, ensuring that notes are saved and accessible even when the app is closed.

## Screenshots
[Drag & drop screenshots here or use imgur and reference them using imgsrc]

- **Ref 1: Home Screen with Note List**  
  ![Home Screen](https://github.com/Avwaveaf/screenshots/blob/main/Screenshot_1728292473.png)
  *This screenshot shows the main note list screen where users can view, add, or delete notes.*

- **Ref 2: Note Creation Screen**  
  ![Note Creation Screen](https://github.com/Avwaveaf/screenshots/blob/main/Screenshot_1728292517.png)
  *The note creation screen allows users to input the note title and content.*

- **Ref 3: Search Functionality**  
  ![Search Screen](https://github.com/Avwaveaf/screenshots/blob/main/Screenshot_1728292534.png)
  *A SearchView is integrated into the app to allow users to filter notes based on keywords in the title or content.*

## Steps
1. Install the required Android dependencies such as Kotlin, Android Jetpack libraries (ViewModel, LiveData, Room).
2. Set up the project following the MVVM architecture pattern.
3. Implement the note management features with proper UI-binding using LiveData.
4. Integrate local data storage using Room, ensuring data persistence.
5. Implement the SearchView to allow users to search and filter notes by keywords dynamically.
