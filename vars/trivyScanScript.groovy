def vulnerability(Map config = [:]) {
    loadScript(name: trivy.sh)
    sh "./trivy.sh ${config.imageName} ${config.severity} ${config.exitCode}"
}

def reportsConverter() {
    sh '''
        trivy convert \
            --format template --template "@/usr/local/share/trivy/templates/html.tpl" \
            --output trivy-image-MEDIUM-results.html trivy-image-MEDIUM-results.json
                        
        trivy convert \
            --format template --template "@/usr/local/share/trivy/templates/html.tpl" \
            --output trivy-image-CRITICAL-results.html trivy-image-CRITICAL-results.json

        trivy convert \
            --format template --template "@/usr/local/share/trivy/templates/junit.tpl" \
            --output trivy-image-MEDIUM-results.xml trivy-image-MEDIUM-results.json
                        
        trivy convert \
            --format template --template "@/usr/local/share/trivy/templates/junit.tpl"\
            --output trivy-image-CRITICAL-results.xml trivy-image-CRITICAL-results.json
    '''
}