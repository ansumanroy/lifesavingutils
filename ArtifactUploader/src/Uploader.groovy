

@Grab(group= 'org.apache.commons', module= 'commons-io', version= '1.3.2')
@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')
import groovy.io.FileType
import org.apache.commons.io.FilenameUtils
import static groovyx.net.http.ContentType.*

def list = []
def dir = new File('/Users/ansumanroy/Desktop/EB/PRODUCT_EAR')
dir.eachFileRecurse (FileType.FILES) { file ->
    list << file
}

def artiUser ='admin'
def artiPassword ='password'
def artiURL='http://127.0.0.1:8081/artifactory/ext-release-local/DEVOPS'


list.each {

    def originalFileName=it.name

    if(!it.name.startsWith(".")){
        def version='1.0'
        def extension=FilenameUtils.getExtension("$it.path")
        def fileNameWithoutExtension=FilenameUtils.removeExtension(it.name)
        def fileInArti=fileNameWithoutExtension+'-'+version+'.'+extension

        println "Uploading $it.path"
        def proc = "curl -v --header Content-Type: application/zip --user $artiUser:$artiPassword --data-binary @${it.path} -X PUT $artiURL/${fileNameWithoutExtension}/$version/$fileInArti"

        println proc
         def process=proc.execute()
          process.waitFor()
          println process.err.text

    }


}
