import spock.lang.Specification
import spock.lang.Unroll

class Test extends Specification {



    @Unroll("The calculate of #name length should be #length")
    def "should calculate the length of a name"() {
        expect:
         name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }
}
