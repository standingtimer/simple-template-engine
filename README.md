# Simple Template Engine

## How it works
It takes variables from config, injects them into templates and writes outputs into results. 
Supports multiple configs, so each of them will produce separate result.

## Configuration
Configs and templates are required.

### Configs
Located in `configs` folder, 1 or several JSON files. Each file is map - key and value.

### Templates
Located in `template` folder. May have any number of files. Each file will be copied into result folder under the same name. 
Files that ends with `.html`, `.firebaserc`, `.json` must be Mustache templates and Mustache variables that match keys defined in configs, will be replaced with config values.
Other files will be copied as is.

## Launching
Requires Java 8 or later. Must be running from folder that contains `configs` and `template`. To run just build and execute `com.standingtimer.ste.Main` file without any parameters.
