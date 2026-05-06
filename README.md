# Lost and Found App - SIT305 Task 7.1 / 9.1

An Android application built for SIT305 that uses SQLite to let users report and browse lost or found items, with category filtering, image uploads, timestamped posts, Google Maps integration, Places autocomplete, and radius-based search.

## Features

- Report an item as Lost or Found with full details
- Browse all posted items in a scrollable list
- Filter items by category using Material3 filter chips
- Upload an image for each post (stored in internal storage)
- Automatic date/time stamp on every listing
- Date picker for selecting when the item was lost or found
- View full item details and remove posts from the database
- Google Places autocomplete for selecting a location
- Get current device location with reverse geocoding
- View all items on a Google Map with color-coded markers (red for Lost, green for Found)
- Radius-based search to show only items within X km of the user

## Tech Stack

- Java
- SQLite (via SQLiteOpenHelper)
- Material Design 3
- Google Maps SDK for Android
- Google Places API
- Fused Location Provider
- ActivityResultContracts for image picking and autocomplete

## Project Structure

- `Item.java` - Model class representing a lost/found post with location coordinates
- `DatabaseHelper.java` - SQLite helper handling table creation and all CRUD operations
- `ItemAdapter.java` - RecyclerView adapter for displaying items in the list
- `MainActivity.java` - Home screen with navigation to create, browse, or view map
- `CreateAdvertActivity.java` - Form screen with autocomplete, current location, image upload, and date picker
- `ListItemsActivity.java` - Displays all items with category filter chips
- `DetailActivity.java` - Shows full details of a selected item with a remove button
- `MapActivity.java` - Google Map showing all items as markers with radius-based filtering

## Layouts

- `activity_main.xml` - Home screen with three navigation buttons
- `activity_create_advert.xml` - Form with radio buttons, text fields, category dropdown, Places autocomplete, current location, image upload, and date picker
- `activity_list_items.xml` - List screen with horizontal chip group filter and RecyclerView
- `activity_detail.xml` - Detail view with image, all fields, and remove button
- `item_row.xml` - Card layout for each item in the list
- `activity_map.xml` - Map screen with radius input and filter controls
