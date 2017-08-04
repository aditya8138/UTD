object Solution {
    
    def countAndSay(n: Int): String = {
        val update = (s:String) => (s.splitAt(s.length-2) 
            match { case (a,b) => a + (b.toInt+10).toString })
        val f = ((s:String) => 
            s.foldLeft[String]("")((b:String, h:Char) => 
                if (b != "" && h == b.last) update(b) else b + "1" + h))
        return if (n > 1) f(countAndSay(n-1)) else "1"
    }
}