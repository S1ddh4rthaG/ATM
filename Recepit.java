/*
,------------------------------------------------------------.
|Recepit                                                     |
|------------------------------------------------------------|
|+uniqueID: String                                           |
|+location: String                                           |
|+report: Report                                             |
|+Recepit(uniqueID: String, location: String, report: Report)|
`------------------------------------------------------------'

*/

//Contains receipt details
public class Recepit {
    public String uniqueID;
    public String location;

    public Report report;

    public Recepit(String uniqueID, String location, Report report){
        this.location = location;
        this.uniqueID = uniqueID;
        this.report   = report;
    }
}
