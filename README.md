# lastSoup
Java command-line application that allows you to extract songs and artists OR artists and album names from Last.fm lists

# running
Since lastsoup is a command line application, you can either compile it in Eclipse (or a similar IDE) and run it from there OR you can run it in your favorite terminal. For interactive terminal mode, you can use this:

java -jar LastSoup.jar

Alternatively, you can manually specify args so as to skip interative mode:

java -jar LastSoup.jar "<your-link-here>" <option>

Note that it is necessary to wrap a link in quotation marks. Option 1 = Albums & artists; Option 2 = Songs & artists. The default option is albums & artists. 
