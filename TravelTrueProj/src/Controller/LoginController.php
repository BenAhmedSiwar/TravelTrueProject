<?php

namespace App\Controller;

use App\Entity\User;

use App\Form\FormType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;

class LoginController extends AbstractController
{
    /**
     * @Route("/connexion", name="login")
     */
    public function login( AuthenticationUtils $authenticationUtils){
        $error = $authenticationUtils->getLastAuthenticationError();

        return $this->render('login/index.html.twig' , [
            'error'=>$error
        ]);

    }
    /**
     * @Route("/deconnexion", name="logout")
     */
    public function logout(){}

    /**
     * @Route("/inscription", name="security_registration")
     */
    public function registration( Request $request ,  EntityManagerInterface $manager ,
                                  UserPasswordEncoderInterface $encoder){

        $user=new User();
        $form= $this->createForm(FormType::class, $user);
        $form->handleRequest($request);


        if ($form->isSubmitted() && $form->isValid()){

            $hash = $encoder->encodePassword($user , $user->getPassword());
            $user->setPassword($hash);
            $manager->persist($user);
            $manager->flush();
            return $this->redirectToRoute("login");
        }



        return $this->render('signup/index.html.twig',[

            'form'=>$form->createView()
        ]);
    }

    /**
     * @Route("/front", name="front")
     */
    public function index(): Response
    {
        return $this->render('front.html.twig', [
            'controller_name' => 'UserController',
        ]);
    }
}
