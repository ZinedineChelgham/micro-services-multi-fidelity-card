# Path: download-latest.sh

BACKEND_REPO=http://134.59.213.143:8004/artifactory/libs-release-local/team-m/backend/1.0/

LATEST_BACKEND=`curl -s $BACKEND_REPO | grep -E backend-[0-9\\.]+ | sed "s/<[^>]\\+>//g" | cut -d " " -f 1 | sort -V -r | head -n 1`
LATEST_BACKEND=${LATEST_BACKEND%.*}

# download with curl


cd backend && curl -s $BACKEND_REPO/$LATEST_BACKEND.jar -o backend.jar

cd ..

CLI_REPO=http://134.59.213.143:8004/artifactory/libs-release-local/team-m/cli/1.0/

LATEST_CLI=`curl -s $CLI_REPO | grep -E cli-[0-9\\.]+ | sed "s/<[^>]\\+>//g" | cut -d " " -f 1 | sort -V -r | head -n 1`
LATEST_CLI=${LATEST_CLI%.*}
cd cli && curl -s $CLI_REPO/$LATEST_CLI.jar -o cli.jar



