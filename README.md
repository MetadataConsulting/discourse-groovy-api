Groovy Discourse API
-------------------------------

Lightweight Groovy Discourse API client rewritten from https://github.com/discourse/discourse_api
 
 
## Using command line tool

Currently the command line tool only allows you to download the latest backup from the server.

  1. First download the latest binary release from https://github.com/MetadataRegistry/discourse-groovy-api/releases/
  2. Extract the zip or tar file
  3. create file `discourse.properties` with following keys: `discourse.url`, `discourse.key` and `discourse.user`
  4. run the script
  
  ```
    discourse backup path/to/discourse.properties path/to/backups/folder
  ```
  