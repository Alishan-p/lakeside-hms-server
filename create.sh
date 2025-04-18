# CREATE THE REST CONTROLLER SERVICE AND REPOSITORY FOR A ENTITY

if [[ -z $1 ]]
then
    echo "Should pass name of the entity as the first and only argument";
else
    ENTITY_NAME=$1;
    cd src/main/java/com/springbootweb/hms_server
    arr=("Controller" "Repository" "Service")
    for name in "${arr[@]}"
    do
        cd "${name,,}"
        touch $ENTITY_NAME$name.java
        cd ..
    done
fi

    