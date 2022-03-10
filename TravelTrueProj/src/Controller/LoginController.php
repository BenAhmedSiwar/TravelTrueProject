<?php

namespace App\Controller;
use App\Form\UserType;
use App\Entity\User;
use App\Form\FormType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use Symfony\Component\Security\Core\Security;
use Symfony\Contracts\HttpClient\HttpClientInterface;

class LoginController extends AbstractController
{  private $client;

    public function __construct(Security $security , HttpClientInterface $client)
    {

       $this->security = $security;
       $this->client = $client;

    }
    
 /**
     * @Route("/", name="home")
     */
    public function Home(){
        $user = $this->security->getUser() ;
        if (  $user && $user->getRole() =='admin'){

          return $this->redirectToRoute('statistiques') ;
         
        }
        
        return $this->render('home/index.html.twig' , [
        ]);

    }




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
            $user->setRole('user');
            $user->setCreatedAt();
            $manager->persist($user);
            $manager->flush();
            $response = $this->client->request(
                'POST',
                'http://localhost:3000/verification/'.$user->getId());
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
        $user = $this->security->getUser() ;
        return $this->render('front.html.twig', [
            'controller_name' => 'UserController',
            'user' => $user 
        ]);
    }



       /**
        * @Route("/inscription/admin", name="admin")
         */
    public function AdminInscription( Request $request ,  EntityManagerInterface $manager ,
    UserPasswordEncoderInterface $encoder){

         $user=new User();
        $form= $this->createForm(FormType::class, $user);
        $form->handleRequest($request);


         if ($form->isSubmitted() && $form->isValid()){

            $hash = $encoder->encodePassword($user , $user->getPassword());
            $user->setPassword($hash);
            $user->setRole('admin');
            $user->setCreatedAt();
            $manager->persist($user);
            $manager->flush();
            $response = $this->client->request(
           'POST',
          'http://localhost:3000/verification/'.$user->getId());
           return $this->redirectToRoute("utilisateurs");

}



          return $this->render('user/admin.html.twig',[

         'form'=>$form->createView()
]);
}
}