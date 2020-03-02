# Simple Template Engine

STE is tool that can create multiple sets of files from single source set of files, by copying source files and replacing placeholders in them with pre-configured result values.  
For example, this is useful for AB testing, to create multiple static web sites that use common structure, but have different headers, titles, etc.

## How it works
It takes variables from config, injects them into templates and writes outputs into results. 
Supports multiple configs, so each of them will produce separate result.

## Configuration

### Configs
Located in `configs` folder, 1 or several JSON files. Each file is map - with keys and values.

### Templates
Located in `template` folder. May have any number of files. Each file will be copied into result folder under the same name. 
Files that ends with `.html`, `.firebaserc`, `.json` must be Mustache templates and Mustache variables that match keys defined in configs, will be replaced with config values.
Other files will be copied as is.

## Launching
Requires Java 8 or later. Must be running from folder that contains `configs` and `template`. To run just build and execute `com.standingtimer.ste.Main` file without any parameters.
