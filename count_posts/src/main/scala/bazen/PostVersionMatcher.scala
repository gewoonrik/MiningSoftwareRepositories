package bazen

class PostVersionMatcher(val versionsWithReleaseDate : Map[String, Any]) {

  def postContainsVersion(version : String, title: String, body : String): Boolean = {
    if(version.matches("[0-9]\\.0\\.0")) {
      val majorVersionNumber = version.split('.')(0)
      if(title.contains(" "+majorVersionNumber+".0 ") || body.contains(" "+majorVersionNumber+".0 ")) {
        return true
      }
    }
    return title.contains(version) || body.contains(version)
  }

  def cleanupText(text : String): String = {
    text
      .replaceAll("<code>.*</code>"," ") // Remove code samples
      .replaceAll("bootstrap", " ") // Remove obvious keywords
      .replaceAll("https?://[^\\s<>\"]+|www\\.[^\\s<>\"]+", " ") // Remove links
      .replaceAll("<[^<]+?>", " ") // Remove HTML
      .replaceAll("&[#a-zA-Z ]+?;", " ") // Remove special characters
  }

  def annotateVersions(posts : List[Post]): Map[String, Post] = {
    return posts.map(v => null, null).toMap
  }
}

class Post(val title : String, val body : String, val date : java.sql.Date, val id : Int);