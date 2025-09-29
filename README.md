## How to use the app
Clone the repo, open it in Android Studio, and press Run to launch on an emulator or device.

## Explanation
App demonstrates how to build a contact list with alphabetical grouping in Jetpack Compose using `LazyColumn` and `StickyHeader`.
- Contact Generation: The app generates 50 randome sample contacts and sorts them alphabetically.
- Sticky Headers: Contacts are group by their first letter, with headers that stay visible while scrolling.
- Scroll to Top FAB: A floating action button appears only after scrolling past the 10th item.
  - Tapping the Fab smoothly scrolls back to the top using `animateScrollToItem()`.
