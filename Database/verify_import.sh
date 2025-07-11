#!/bin/bash
# Database Import Verification Script
# Pakistan Election System

echo "🔍 Election System Database Import Verification"
echo "=============================================="

# Check if phpMyAdmin is accessible
echo "📡 Testing phpMyAdmin accessibility..."
if curl -s http://localhost/phpmyadmin > /dev/null; then
    echo "✅ phpMyAdmin is accessible at http://localhost/phpmyadmin"
else
    echo "❌ phpMyAdmin is not accessible. Check if XAMPP is running."
    exit 1
fi

# Test MySQL connection
echo "🔐 Testing MySQL connection..."
if /opt/lampp/bin/mysql -u root -p'junaid.s' --socket=/opt/lampp/var/mysql/mysql.sock -e "SELECT VERSION();" 2>/dev/null; then
    echo "✅ MySQL connection successful"
    
    # Check if election_system database exists
    echo "🗄️  Checking election_system database..."
    if /opt/lampp/bin/mysql -u root -p'junaid.s' --socket=/opt/lampp/var/mysql/mysql.sock -e "USE election_system; SHOW TABLES;" 2>/dev/null; then
        echo "✅ election_system database exists with tables"
        
        # Check table counts
        echo "📊 Database statistics:"
        /opt/lampp/bin/mysql -u root -p'junaid.s' --socket=/opt/lampp/var/mysql/mysql.sock election_system -e "
        SELECT 'Provinces' as Table_Name, COUNT(*) as Count FROM provinces
        UNION ALL
        SELECT 'Cities', COUNT(*) FROM cities  
        UNION ALL
        SELECT 'Divisions', COUNT(*) FROM divisions
        UNION ALL
        SELECT 'Parties', COUNT(*) FROM parties
        UNION ALL
        SELECT 'Voters', COUNT(*) FROM voters
        UNION ALL
        SELECT 'Elections', COUNT(*) FROM elections
        UNION ALL
        SELECT 'Votes', COUNT(*) FROM votes;" 2>/dev/null
        
    else
        echo "❌ election_system database not found. Import the SQL file first."
    fi
    
else
    echo "❌ MySQL connection failed. Try manual import through phpMyAdmin."
fi

echo ""
echo "📋 Next Steps:"
echo "1. If database doesn't exist, import: election_system_phpmyadmin.sql"
echo "2. Access phpMyAdmin: http://localhost/phpmyadmin"
echo "3. Use credentials: root / junaid.s"
echo "4. Test Java connection with: javac test_connection.java && java TestConnection"
