# Lost and Found App - SIT305 Task 7.1

An Android application built for SIT305 Task 7.1 that uses SQLite to let users report and browse lost or found items, with category filtering, image uploads, and timestamped posts.

## Features

- Report an item as Lost or Found with full details
- Browse all posted items in a scrollable list
- Filter items by category using Material3 filter chips
- Upload an image for each post (stored in internal storage)
- Automatic date/time stamp on every listing
- Date picker for selecting when the item was lost or found
- View full item details and remove posts from the database

## Tech Stack

- Java
- SQLite (via SQLiteOpenHelper)
- Material Design 3
- ActivityResultContracts for image picking

## Project Structure

- `Item.java` - Model class representing a lost/found post
- `DatabaseHelper.java` - SQLite helper handling table creation and all CRUD operations
- `ItemAdapter.java` - RecyclerView adapter for displaying items in the list
- `MainActivity.java` - Home screen with navigation to create or browse posts
- `CreateAdvertActivity.java` - Form screen for submitting a new lost/found item
- `ListItemsActivity.java` - Displays all items with category filter chips
- `DetailActivity.java` - Shows full details of a selected item with a remove button

## Layouts

- `activity_main.xml` - Home screen with two navigation buttons
- `activity_create_advert.xml` - Form with radio buttons, text fields, category dropdown, image upload, and date picker
- `activity_list_items.xml` - List screen with horizontal chip group filter and RecyclerView
- `activity_detail.xml` - Detail view with image, all fields, and remove button
- `item_row.xml` - Card layout for each item in the list
