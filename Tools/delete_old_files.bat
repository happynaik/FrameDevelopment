forfiles /m *.html /p "E:\Automation\Eclipse_Workspace_SFDC2\SALESFORCE_Automation\test_output" /c "del @path" /D -1  2>nul 1>nul 
forfiles /m *.html /p "E:\Automation\Eclipse_Workspace_SFDC2\SALESFORCE_Automation\test_output\Screenshots" /c "del @path" /D -1  2>nul 1>nul 
exit