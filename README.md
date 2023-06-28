# JSubtitle-Editor

<img src="https://github.com/Oxmoon/jsubtitle-editor/blob/master/images/gui_image.png" style=" width:500px ; height:300px "  >

## Description
.srt file editing program written in java using a java swing GUI.

## Usage
Batch edit .srt files from a given directory.  
The program will only edit .srt files and will preserve
the name of the original file in a folder named:`<directory_name>/jsubtitle-editor/` 

### Functionality

* Move timestamps forward and backward.
* Remove or replace characters/words/phrases.
* **Note:** The 'Find' field can take multiple values seperated by commas. Ex: !, z, word.
* Remove all text between (and including) two given characters, for example, between parenthesis ( ).
* Cleanup removes additional usages of the characters from the Starting and Ending field.
* Also works with Japanese and Chinese character sets.

### Troubleshooting
I recommend putting the folder you will work with close to your user folder, as Java
may not be able to access a folder that is being used in another process.

## Credit
github@[killergerbah](https://github.com/killergerbah/jsubtitle)  
JSubtitle-Editor uses killergerbah's JSubtitle parser for SRTs.

## License
Copyright © 2023 [Gage Hilyard](https://github.com/Oxmoon/).  
This project is [MIT licensed](LICENSE.md).
