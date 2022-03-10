<?php

namespace App\Repository;

use App\Entity\User;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method User|null find($id, $lockMode = null, $lockVersion = null)
 * @method User|null findOneBy(array $criteria, array $orderBy = null)
 * @method User[]    findAll()
 * @method User[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class UserRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, User::class);
    }

    // /**
    //  * @return User[] Returns an array of User objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('u')
            ->andWhere('u.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('u.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    
    public function UsersByMonth()
    {

        $conn = $this->getEntityManager()->getConnection();

        $sql = '
            SELECT Count(id) AS Stat , MONTH(created_at) As siwar FROM `user` WHERE created_at > DATE_SUB(now(), INTERVAL 6 MONTH ) GROUP BY MONTH(created_at)
            
            ';
        $stmt = $conn->prepare($sql);
        $resultSet = $stmt->executeQuery();
         // returns an array of arrays (i.e. a raw data set)
         return $resultSet->fetchAllAssociative();
    }
    



}
