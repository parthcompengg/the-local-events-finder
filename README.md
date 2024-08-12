# final-project-the-local-event-finder-g13

- !! IMPORTANT: When commiting changes, don't commit local.properties or ANY app.build filed !!

- Members: Baptiste Boiteux, Emily West, Parth Shah, Garrett Berliner

- High-level description: A mobile app that grabs location to display events that are happening around you via zip code. 

- Link to 3rd-party API: https://app.ticketmaster.com/discovery/v2/ (Examples we will follow: https://developer.ticketmaster.com/products-and-docs/apis/discovery-api/v2/#search-events-v2)
Explanation of the API methods your app will call: We will need to make an API key, then call what specific event categories based on the genre the user selects from our home screen. The seatgeek API has calls for event search, get event details, get attraction details (to grab location), get classification details, and get sub-genre details. We will use all these calls to grab data essential for our app to work - i.e. the event search call will grab information for events within a given zipcode (supplied by the user), then we use that even id to grab event details, etc.

- A description of how the UI for your app will be organized:
The UI for our app will be organized through a primary home screen that has 6 main categories for events - Music, Social, Sports, Networking, Cultural, and Religious.  We are imagining the initial events within each category would all be in Portland, Oregon - just so when we change the location to Corvallis different data will be displayed. This screen also will have a ‘grab location’ button which will provide the option for our app to grab their zip code from their direct location. There will be a text box allowing users to enter keywords to find events and a toggle menu to help with navigation between the multiple screens. The user will have preferences through user profile data, which will be properly accessed through the Login Screen with correct credentials. For application data, the user will have the ability to favorite events & this information will be within their device data.

- A description of the additional feature not covered in class that your app will implement: 1) User can add pictures of an event (camera access)   2) Besides just allowing the user to put in a zip code - provide an option to get that data from the device location when they open the app (access direct location). 3) Post pictures after the event (file access - photos) 4) Allow the option to share photos to external social media (share menu & open one app [i.e. Instagram])

- Division of labor for the project between members:
Front: Emily & Garrett
Middleware API:  Parth & Garrett
Back (Database): Parth & Baptiste



