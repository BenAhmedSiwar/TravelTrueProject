<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\UserType;
use App\Entity\User;
use Symfony\Component\Security\Core\Security;



class LocationController extends AbstractController
{
    
    public function __construct(Security $security)
    {
       $this->security = $security;
    }
    

    /**
     * @Route("/stat", name="statistiques")
     */
    public function Statistiques (){

        $user = $this->security->getUser() ;
        if (  $user && $user->getRole() =='user'){

          return $this->redirectToRoute('home') ;
        }
        return $this->render('location/index.html.twig', [
            'controller_name' => 'LocationController',
        ]);
    }
    
}
