# Initializes a postgres sql db for this project.
# On debian, need to make /var/run/postgres world-writable

if ! [ -f init_db.sh ]; then
    echo "Run this from your app's root folder, please." >&2
    exit 1
fi

/usr/lib/postgresql/9.3/bin/initdb pg
/usr/lib/postgresql/9.3/bin/postgres -D pg &
/usr/lib/postgresql/9.3/bin/createdb swenghw6grader

echo "export DATABASE_URL=postgresql://localhost:5432/swenghw6grader" > setup.env
