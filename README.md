Groovy Discourse API
-------------------------------

Lightweight Groovy Discourse API client rewritten from https://github.com/discourse/discourse_api
 
 
## Using command line tool

Currently the command line tool only allows you to download the latest backup from the server.

  1. First download the latest binary release from https://github.com/MetadataRegistry/discourse-groovy-api/releases/
  2. Extract the zip or tar file
  3. create file `discourse.properties` with following keys: and 
  
  Key               | Comment
  ----------------- | -----------------------------------------------------------------------
  `discourse.url`   | The base URL of the server, e.g. `https://discourse.example.com/`
  `discourse.key`   | The API key generated at `/admin/api` page of your discourse server
  `discourse.user`  | The user for who were the key generated
  
  4. run the script
  
  ```
    discourse backup path/to/discourse.properties path/to/backups/folder
  ```
  