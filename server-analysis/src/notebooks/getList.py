import pymysql

# 데이터베이스 연결 함수
def get_db_connection():
    return pymysql.connect(user='root', passwd='1004', host='localhost', db='cmsplusmain', charset='utf8')

def get_member_list(page: int = 1, page_size: int = 10):
    offset = (page - 1) * page_size
    query = """
    SELECT 
        m.member_id,
        YEAR(m.member_enroll_date) as enroll_year,
        DATEDIFF(c.contract_end_date, c.contract_start_date) as contract_duration,
        SUM(cp.contract_product_price * cp.contract_product_quantity) as total_contract_amount,
        p.payment_method
    FROM 
        member m
    JOIN 
        contract c ON m.member_id = c.member_id
    JOIN 
        contract_product cp ON c.contract_id = cp.contract_id
    LEFT JOIN 
        payment p ON c.payment_id = p.payment_id
    GROUP BY 
        m.member_id, YEAR(m.member_enroll_date), c.contract_id, c.contract_start_date, c.contract_end_date, p.payment_method
    LIMIT %s OFFSET %s
    """
    count_query = "SELECT COUNT(DISTINCT m.member_id) FROM member m"
    
    conn = get_db_connection()
    try:
        with conn.cursor() as cursor:
            cursor.execute(query, (page_size, offset))
            members = cursor.fetchall()
            
            cursor.execute(count_query)
            total_count = cursor.fetchone()[0]
        
        member_list = []
        for member in members:
            member_list.append({
                "member_id": member[0],
                "enroll_year": member[1],
                "contract_duration": member[2],
                "total_contract_amount": float(member[3]),
                "payment_method": member[4]
            })
        
        return {
            "members": member_list,
            "total_count": total_count,
            "page": page,
            "page_size": page_size,
            "total_pages": (total_count + page_size - 1) // page_size
        }
    finally:
        conn.close()