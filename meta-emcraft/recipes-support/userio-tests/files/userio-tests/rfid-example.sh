#!/bin/sh

# Set up the terminal speed
stty speed 9600 -echo < /dev/ttyACM0 > /dev/null

while :; do
    # "SearchTag" command
    echo 050010 > /dev/ttyACM0
    # Read the response
    resp=`head -n 1 /dev/ttyACM0`
    # Skip the same response
    [ $? -gt 0 ] && continue
    if [ -n "$resp" -a "$resp" != "$prevresp" ]; then
	# Skip if a tag is not found
	if [ "$resp" != "0000" ]; then
	    # Extract Tag ID from the response
	    id=`echo $resp | cut -c 11-`
	    # Print Tag ID to the console
	    echo "Tag ID:" $id
	    # "Beep" command
	    echo 0407646009f401f401 > /dev/ttyACM0
	    # Drop the response to the "Beep" command
	    head -n 1 /dev/ttyACM0 > /dev/null
	fi
	prevresp=$resp
    fi
done
