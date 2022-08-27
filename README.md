# Upgradation of Jan Dhan Darshak App

## Future that we seek

**Personalization**  
Our app works on both, the google dataset using API keys and custom dataset using some algorithms, thus making it flexible for any needs.

Presently it's just for Ministry of Finance and DFS, but with our custom dataset implementation, any organization can extend our app for their own map services.
One can access, display and perform operations on entries from their custom dataset or from DBs that can only be queried using API keys(Google Maps in our case).

- You can also lookup for entries from sample dataset using voice and text search.
- Admin has been given the privilege to modify dataset which is directly reflected on map.



## Salient features and use cases  

**1) Know all about your nearby financial touch points**
App is mainly focused on displaying info of your nearby financial touch points like ATMs, Banks, Post Offices, CSC and Bank Mitra.
- Complete details: Address, Opening/Closing hours, images
- Navigate to selected location
- Share location to anyone
- Save to favourite locations

**2) Set custom buffer distance**  
Displays the locations in circle of selected radius.
Since Google places API doesn't give you the freedom to select custom radius, we created a basic model for getting specified results from given dataset.
Find our implementation in the distance_filter.inpynb file added.
One can create any model and use it for better results

**3) Filter banks by name**  
Select bank of your choice and our app will display your will.

**3)Multilingual feature**  
Use app in your local language. Our app supports Hindi, English, Telugu, Tamil, Marathi, Kannada, Bengali, Punjabi and Gujarati. (Some minor bugs to be fixed)

**4) Various Map themes**  
Aubergine, Dark , Standard, Retro , Silver, we have it all. Select and use app in map theme
